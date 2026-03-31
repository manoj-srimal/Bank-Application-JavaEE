package lk.assignment.bankapp.core.service;

import jakarta.ejb.Remote;

@Remote
public interface InterestCalculationBeanLocal {
    void calculateAndApplyDailyInterest();
}