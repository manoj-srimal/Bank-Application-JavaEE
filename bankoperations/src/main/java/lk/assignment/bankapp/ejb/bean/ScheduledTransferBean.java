package lk.assignment.bankapp.ejb.bean;

import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.assignment.bankapp.core.exception.InsufficientBalanceException;
import lk.assignment.bankapp.core.exception.InvalidAccountException;
import lk.assignment.bankapp.core.model.Account;
import lk.assignment.bankapp.core.model.ScheduledTask;
import lk.assignment.bankapp.core.model.ScheduledTaskStatus;
import lk.assignment.bankapp.core.model.ScheduledTaskType;
import lk.assignment.bankapp.core.service.FundTransferBeanLocal;
import lk.assignment.bankapp.core.service.ScheduledTransferBeanLocal;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Singleton
public class ScheduledTransferBean implements ScheduledTransferBeanLocal {

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @EJB
    private FundTransferBeanLocal fundTransferBean;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createScheduledTransfer(Long fromUserId, String fromAccountId, String toAccountId, double amount, Date transferDate) throws InvalidAccountException {
        Account fromAccount = em.find(Account.class, fromAccountId);
        if (fromAccount == null || !fromAccount.getUser().getUserId().equals(fromUserId)) {
            throw new InvalidAccountException("Invalid source account specified.");
        }

        Account toAccount = em.find(Account.class, toAccountId);
        if (toAccount == null) {
            throw new InvalidAccountException("Invalid destination account specified.");
        }

        ScheduledTask task = new ScheduledTask();
        task.setSourceAccount(fromAccount);
        task.setDestinationAccount(toAccount);
        task.setAmount(BigDecimal.valueOf(amount));
        task.setNextRunTime(transferDate);
        task.setStatus(ScheduledTaskStatus.PENDING);
        task.setTaskType(ScheduledTaskType.SCHEDULED_TRANSFER);
        task.setScheduleExpression("ONE_TIME");

        em.persist(task);
    }

    @Override
    @Schedule(hour = "*", minute = "*/5", persistent = false)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void executePendingTransfers() {
        System.out.println("SCHEDULER: Checking for pending transfers...");

        List<ScheduledTask> pendingTasks = em.createQuery(
                        "SELECT t FROM ScheduledTask t WHERE t.status = :status AND t.nextRunTime <= :now", ScheduledTask.class)
                .setParameter("status", ScheduledTaskStatus.PENDING)
                .setParameter("now", new Date())
                .getResultList();

        for (ScheduledTask task : pendingTasks) {
            try {
                System.out.println("SCHEDULER: Processing task ID: " + task.getTaskId());

                fundTransferBean.performTransfer(
                        task.getSourceAccount().getUser().getUserId(),
                        task.getSourceAccount().getAccountId(),
                        task.getDestinationAccount().getAccountId(),
                        task.getAmount().doubleValue(),
                        "Scheduled Transfer"
                );

                task.setStatus(ScheduledTaskStatus.COMPLETED);
                System.out.println("SCHEDULER: Task ID " + task.getTaskId() + " COMPLETED.");

            } catch (InsufficientBalanceException | InvalidAccountException e) {

                task.setStatus(ScheduledTaskStatus.FAILED);
                System.err.println("SCHEDULER: Task ID " + task.getTaskId() + " FAILED. Reason: " + e.getMessage());

            } catch (Exception e) {

                System.err.println("SCHEDULER: Unexpected error processing task ID " + task.getTaskId() + ". Will retry later.");
                e.printStackTrace();
            }
            em.merge(task);
        }
    }

    @Override
    public java.util.List<ScheduledTask> getScheduledTaskHistory(String accountId) {
        return em.createQuery(
                        "SELECT t FROM ScheduledTask t WHERE t.sourceAccount.accountId = :accountId ORDER BY t.createdAt DESC",
                        ScheduledTask.class)
                .setParameter("accountId", accountId)
                .setMaxResults(50)
                .getResultList();
    }
}