package lk.assignment.bankapp.core.service;

import jakarta.ejb.Remote;
import lk.assignment.bankapp.core.model.Transaction;

import java.util.List;

@Remote
public interface AdminTransactionBeanLocal {

    List<Transaction> getAllTransactions();
}
