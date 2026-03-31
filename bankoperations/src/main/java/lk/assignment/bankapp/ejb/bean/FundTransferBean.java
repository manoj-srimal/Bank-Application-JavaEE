package lk.assignment.bankapp.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.assignment.bankapp.core.exception.InsufficientBalanceException;
import lk.assignment.bankapp.core.exception.InvalidAccountException;
import lk.assignment.bankapp.core.model.Account;
import lk.assignment.bankapp.core.model.Transaction;
import lk.assignment.bankapp.core.model.TransactionType;
import lk.assignment.bankapp.core.service.FundTransferBeanLocal;

import java.math.BigDecimal;

@Stateless
public class FundTransferBean implements FundTransferBeanLocal {

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void performTransfer(Long fromUserId, String fromAccountId, String toAccountId, double amount, String description)
            throws InsufficientBalanceException, InvalidAccountException, SecurityException {

        BigDecimal transferAmount = BigDecimal.valueOf(amount);

        // 1. Validate accounts
        Account fromAccount = em.find(Account.class, fromAccountId);
        Account toAccount = em.find(Account.class, toAccountId);

        if (fromAccount == null) throw new InvalidAccountException("Your account does not exist.");
        if (toAccount == null) throw new InvalidAccountException("Recipient account does not exist.");
        if (fromAccountId.equals(toAccountId)) throw new InvalidAccountException("Cannot transfer to the same account.");

        // 2. SECURITY CHECK
        if (!fromAccount.getUser().getUserId().equals(fromUserId)) {
            throw new SecurityException("User is not authorized to access this account.");
        }

        // 3. Check for sufficient balance
        if (fromAccount.getBalance().compareTo(transferAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to perform the transfer.");
        }

        // 4. Perform the transfer
        fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
        toAccount.setBalance(toAccount.getBalance().add(transferAmount));

        // 5. Record the transaction
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(transferAmount);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setDescription(description);

        em.persist(transaction);
    }

    @Override
    public java.util.List<Transaction> getTransactionHistory(String accountId) {
        return em.createQuery(
                        "SELECT t FROM Transaction t WHERE t.fromAccount.accountId = :accountId OR t.toAccount.accountId = :accountId ORDER BY t.transactionTimestamp DESC",
                        Transaction.class)
                .setParameter("accountId", accountId)
                .setMaxResults(50)
                .getResultList();
    }
}
