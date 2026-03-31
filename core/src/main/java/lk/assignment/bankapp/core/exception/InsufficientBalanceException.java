package lk.assignment.bankapp.core.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
