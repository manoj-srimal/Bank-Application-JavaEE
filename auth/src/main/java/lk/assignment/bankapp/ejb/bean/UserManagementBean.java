package lk.assignment.bankapp.ejb.bean;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.assignment.bankapp.core.mail.OtpMail;
import lk.assignment.bankapp.core.mail.VerificationMail;
import lk.assignment.bankapp.core.model.LoginOtp;
import lk.assignment.bankapp.core.model.Role;
import lk.assignment.bankapp.core.model.User;
import lk.assignment.bankapp.core.model.UserStatus;
import lk.assignment.bankapp.core.provider.MailServiceProvider;
import lk.assignment.bankapp.core.service.AccountManagementBeanLocal;
import lk.assignment.bankapp.core.service.UserManagementBeanLocal;
import lk.assignment.bankapp.core.util.PasswordUtil;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Stateless
public class UserManagementBean implements UserManagementBeanLocal {

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @EJB
    private AccountManagementBeanLocal accountBean;

    @Override
    public void registerUser(User user) throws Exception {

        if (em.createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", user.getEmail())
                .getResultList().size() > 0) {
            throw new Exception("A user with this email already exists.");
        }

        String hashedPassword = PasswordUtil.hashPassword(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        String verificationCode = UUID.randomUUID().toString();
        user.setVerificationCode(verificationCode);

        user.setStatus(UserStatus.INACTIVE);


        Role customerRole = em.find(Role.class, 2);
        if (customerRole == null) {
            throw new Exception("'CUSTOMER' role not found in the database. Please seed the database.");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);
        user.setRoles(roles);

        em.persist(user);


        VerificationMail verificationMail = new VerificationMail(user.getEmail(), user.getVerificationCode());
        MailServiceProvider.getInstance().sendMail(verificationMail);
    }

    @Override
    public boolean verifyUserAccount(String verificationCode) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.verificationCode = :vc", User.class)
                    .setParameter("vc", verificationCode)
                    .getSingleResult();

            if (user != null && user.getStatus() != UserStatus.ACTIVE) {

                user.setStatus(UserStatus.ACTIVE);
                user.setVerificationCode(null); // Remove the code so it cannot be reused
                em.merge(user);


                try {
                    accountBean.createNewAccount(user.getUserId(), "SAVINGS", 1000.00);
                } catch (Exception e) {

                    System.err.println("CRITICAL: User " + user.getEmail() + " verified, but automatic account creation failed.");
                    e.printStackTrace();
                }

                return true;
            }
        } catch (NoResultException e) {

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    @Override
    public User loginUser(String email, String password) {
        try {

            User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();

            String providedPasswordHash = PasswordUtil.hashPassword(password);

            if (user != null && user.getPasswordHash().equals(providedPasswordHash) && user.getStatus() == UserStatus.ACTIVE) {
                return user;
            }
        } catch (NoResultException e) {

            return null;
        }
        return null;
    }

    @Override
    public void createAndSendLoginOtp(User user) {

        String otp = String.format("%06d", new Random().nextInt(999999));


        LoginOtp loginOtp = new LoginOtp();
        loginOtp.setUser(user);
        loginOtp.setOtpCode(otp);
        loginOtp.setUsed(false);

        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        loginOtp.setExpiresAt(Date.from(expiryTime.atZone(ZoneId.systemDefault()).toInstant()));

        em.persist(loginOtp);

        OtpMail otpMail = new OtpMail(user.getEmail(), otp);
        MailServiceProvider.getInstance().sendMail(otpMail);
    }

    @Override
    public User validateLoginOtp(Long userId, String otp) {
        try {
            LoginOtp otpRecord = em.createQuery(
                            "SELECT o FROM LoginOtp o WHERE o.user.userId = :userId AND o.otpCode = :otp ORDER BY o.expiresAt DESC", LoginOtp.class)
                    .setParameter("userId", userId)
                    .setParameter("otp", otp)
                    .setMaxResults(1)
                    .getSingleResult();

            if (otpRecord != null && !otpRecord.isUsed() && otpRecord.getExpiresAt().after(new Date())) {
                otpRecord.setUsed(true);
                em.merge(otpRecord);

                User user = otpRecord.getUser();
                user.getRoles().size();

                return user;
            }
        } catch (NoResultException e) {
            return null;
        }
        return null;
    }
}