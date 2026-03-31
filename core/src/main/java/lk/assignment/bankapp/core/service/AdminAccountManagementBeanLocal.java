package lk.assignment.bankapp.core.service;

import jakarta.ejb.Remote;
import lk.assignment.bankapp.core.model.Account;

import java.util.List;

@Remote
public interface AdminAccountManagementBeanLocal {
    List<Account> getAllBankAccounts();
}
