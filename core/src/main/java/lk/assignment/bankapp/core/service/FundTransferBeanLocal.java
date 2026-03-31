package lk.assignment.bankapp.core.service;

import jakarta.ejb.Remote;
import lk.assignment.bankapp.core.exception.InsufficientBalanceException;
import lk.assignment.bankapp.core.exception.InvalidAccountException;

@Remote
public interface FundTransferBeanLocal {
    void performTransfer(Long fromUserId, String fromAccountId, String toAccountId, double amount, String description)
            throws InsufficientBalanceException, InvalidAccountException, SecurityException;
    java.util.List<lk.assignment.bankapp.core.model.Transaction> getTransactionHistory(String accountId);
}