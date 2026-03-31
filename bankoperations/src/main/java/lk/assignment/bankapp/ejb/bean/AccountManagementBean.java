package lk.assignment.bankapp.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.assignment.bankapp.core.model.Account;
import lk.assignment.bankapp.core.model.AccountStatus;
import lk.assignment.bankapp.core.model.AccountType;
import lk.assignment.bankapp.core.model.User;
import lk.assignment.bankapp.core.service.AccountManagementBeanLocal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Stateless
public class AccountManagementBean implements AccountManagementBeanLocal {

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public void createNewAccount(Long userId, String accountTypeStr, double initialBalance) throws Exception {

        User user = em.find(User.class, userId);
        if (user == null) {
            throw new Exception("User with ID " + userId + " not found.");
        }

        Account newAccount = new Account();

        long number = ThreadLocalRandom.current().nextLong(100_000_000_000L, 1_000_000_000_000L);
        newAccount.setAccountId(String.valueOf(number));

        newAccount.setUser(user);
        newAccount.setBalance(BigDecimal.valueOf(initialBalance));
        newAccount.setAccountType(AccountType.valueOf(accountTypeStr.toUpperCase()));
        newAccount.setStatus(AccountStatus.ACTIVE);
        newAccount.setOpenedAt(new Date());

        em.persist(newAccount);
    }

    @Override
    public java.util.List<Account> getAccountsForUser(Long userId) {
        return em.createQuery("SELECT a FROM Account a WHERE a.user.userId = :userId", Account.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}