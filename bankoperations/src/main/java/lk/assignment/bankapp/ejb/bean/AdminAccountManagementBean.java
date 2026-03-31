package lk.assignment.bankapp.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.assignment.bankapp.core.model.Account;
import lk.assignment.bankapp.core.service.AdminAccountManagementBeanLocal;

import java.util.List;

@Stateless
public class AdminAccountManagementBean implements AdminAccountManagementBeanLocal {

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public List<Account> getAllBankAccounts() {
        return em.createQuery("SELECT a FROM Account a JOIN FETCH a.user ORDER BY a.openedAt DESC", Account.class)
                .getResultList();
    }
}
