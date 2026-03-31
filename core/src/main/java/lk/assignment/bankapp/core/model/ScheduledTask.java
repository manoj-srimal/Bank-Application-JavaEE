package lk.assignment.bankapp.core.model;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "scheduled_task")
public class ScheduledTask implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    // කාර්යය ආරම්භ වන ගිණුම
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    // මුදල් බැර වන ගිණුම (transfer සඳහා පමණි)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id") // Nullable for interest calculation
    private Account destinationAccount;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type", nullable = false)
    private ScheduledTaskType taskType;

    @Column(name = "schedule_expression", nullable = false)
    private String scheduleExpression; // Cron expression

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ScheduledTaskStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "next_run_time")
    private Date nextRunTime;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    // Getters and Setters

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ScheduledTaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(ScheduledTaskType taskType) {
        this.taskType = taskType;
    }

    public String getScheduleExpression() {
        return scheduleExpression;
    }

    public void setScheduleExpression(String scheduleExpression) {
        this.scheduleExpression = scheduleExpression;
    }

    public ScheduledTaskStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduledTaskStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getNextRunTime() {
        return nextRunTime;
    }

    public void setNextRunTime(Date nextRunTime) {
        this.nextRunTime = nextRunTime;
    }
}