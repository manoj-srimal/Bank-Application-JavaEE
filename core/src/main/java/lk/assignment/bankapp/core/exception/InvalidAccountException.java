package lk.assignment.bankapp.core.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class InvalidAccountException extends Exception {
    public InvalidAccountException(String message) {
        super(message);
    }
}