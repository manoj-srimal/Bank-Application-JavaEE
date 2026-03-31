package lk.assignment.bankapp.core.service;


import jakarta.ejb.Remote;
import lk.assignment.bankapp.core.model.Account;

@Remote
public interface AccountManagementBeanLocal {
    void createNewAccount(Long userId, String accountType, double initialBalance) throws Exception;
    java.util.List<lk.assignment.bankapp.core.model.Account> getAccountsForUser(Long userId);
}
