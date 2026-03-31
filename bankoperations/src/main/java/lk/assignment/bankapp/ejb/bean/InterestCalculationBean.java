package lk.assignment.bankapp.ejb.bean;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.assignment.bankapp.core.model.Account;
import lk.assignment.bankapp.core.model.AccountStatus;
import lk.assignment.bankapp.core.model.AccountType;
import lk.assignment.bankapp.core.model.Transaction;
import lk.assignment.bankapp.core.model.TransactionType;
import lk.assignment.bankapp.core.service.InterestCalculationBeanLocal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Singleton
public class InterestCalculationBean implements InterestCalculationBeanLocal {

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    private static final BigDecimal DAILY_INTEREST_RATE = new BigDecimal("0.0001");

    @Override
    @Schedule(hour = "10", minute = "0", second = "0", persistent = false, info = "Daily Interest Calculation Timer")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void calculateAndApplyDailyInterest() {
        System.out.println("INTEREST_CALC_JOB: Starting daily interest calculation...");

        List<Account> eligibleAccounts = em.createQuery(
                        "SELECT a FROM Account a WHERE a.status = :status AND a.accountType = :type", Account.class)
                .setParameter("status", AccountStatus.ACTIVE)
                .setParameter("type", AccountType.SAVINGS)
                .getResultList();

        if (eligibleAccounts.isEmpty()) {
            System.out.println("INTEREST_CALC_JOB: No eligible accounts found. Job finished.");
            return;
        }

        System.out.println("INTEREST_CALC_JOB: Found " + eligibleAccounts.size() + " eligible accounts.");

        for (Account account : eligibleAccounts) {
            try {

                BigDecimal interestAmount = account.getBalance().multiply(DAILY_INTEREST_RATE);

                if (interestAmount.compareTo(new BigDecimal("0.01")) >= 0) {

                    interestAmount = interestAmount.setScale(2, RoundingMode.HALF_UP);

                    account.setBalance(account.getBalance().add(interestAmount));

                    Transaction interestTransaction = new Transaction();
                    interestTransaction.setToAccount(account);
                    interestTransaction.setAmount(interestAmount);
                    interestTransaction.setTransactionType(TransactionType.INTEREST);
                    interestTransaction.setDescription("Daily Interest Credit");

                    em.persist(interestTransaction);
                    em.merge(account);
                }
            } catch (Exception e) {

                System.err.println("INTEREST_CALC_JOB: Failed to process interest for account " + account.getAccountId() + ". Error: " + e.getMessage());
            }
        }
        System.out.println("INTEREST_CALC_JOB: Daily interest calculation job finished.");
    }
}