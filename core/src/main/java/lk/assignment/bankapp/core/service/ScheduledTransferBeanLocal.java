package lk.assignment.bankapp.core.service;

import java.util.Date;
import jakarta.ejb.Remote;
import lk.assignment.bankapp.core.exception.InvalidAccountException;

@Remote
public interface ScheduledTransferBeanLocal {

    /**
     * Creates a new scheduled transfer record in the database.
     * @param fromUserId The ID of the user initiating the transfer.
     * @param fromAccountId The source account number.
     * @param toAccountId The recipient account number.
     * @param amount The amount to transfer.
     * @param transferDate The future date on which to execute the transfer.
     * @throws InvalidAccountException if the from account does not belong to the user.
     */
    void createScheduledTransfer(Long fromUserId, String fromAccountId, String toAccountId, double amount, Date transferDate) throws InvalidAccountException;

    void executePendingTransfers();
    java.util.List<lk.assignment.bankapp.core.model.ScheduledTask> getScheduledTaskHistory(String accountId);
}