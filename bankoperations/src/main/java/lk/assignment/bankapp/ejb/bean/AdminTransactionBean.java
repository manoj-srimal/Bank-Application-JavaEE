package lk.assignment.bankapp.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.assignment.bankapp.core.model.Transaction;
import lk.assignment.bankapp.core.service.AdminTransactionBeanLocal;

import java.util.List;

@Stateless
public class AdminTransactionBean implements AdminTransactionBeanLocal {

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public List<Transaction> getAllTransactions() {
        return em.createQuery(
                        "SELECT t FROM Transaction t " +
                                "LEFT JOIN FETCH t.fromAccount " +
                                "LEFT JOIN FETCH t.toAccount " +
                                "ORDER BY t.transactionTimestamp DESC",
                        Transaction.class)
                .setMaxResults(100)
                .getResultList();
    }
}
