package lk.assignment.bankapp.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.assignment.bankapp.core.model.User;
import lk.assignment.bankapp.core.model.UserStatus;
import lk.assignment.bankapp.core.service.AdminUserManagementBeanLocal;

import java.util.List;

@Stateless
public class AdminUserManagementBean implements AdminUserManagementBeanLocal {

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        return em.createQuery(
                        "SELECT u FROM User u WHERE NOT EXISTS (SELECT r FROM u.roles r WHERE r.roleName = 'ADMIN')", User.class)
                .getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void toggleUserStatus(Long userIdToToggle, Long adminUserId) throws Exception {
        User userToToggle = em.find(User.class, userIdToToggle);

        if (userToToggle == null) {
            throw new Exception("User not found.");
        }

        if (userToToggle.getUserId().equals(adminUserId)) {
            throw new Exception("Admin users cannot change their own status.");
        }

        if (userToToggle.getStatus() == UserStatus.ACTIVE) {
            userToToggle.setStatus(UserStatus.INACTIVE);
        } else {
            userToToggle.setStatus(UserStatus.ACTIVE);
        }
        em.merge(userToToggle);
    }

    @Override
    public long getTotalUserCount() {
        return em.createQuery("SELECT COUNT(u) FROM User u WHERE NOT EXISTS (SELECT r FROM u.roles r WHERE r.roleName = 'ADMIN')", Long.class)
                .getSingleResult();
    }

    @Override
    public long getTotalAccountCount() {
        return em.createQuery("SELECT COUNT(a) FROM Account a", Long.class)
                .getSingleResult();
    }
}