package lk.assignment.bankapp.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.assignment.bankapp.core.model.Transaction;
import lk.assignment.bankapp.core.service.AdminTransactionBeanLocal;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminTransactionServlet", value = "/admin/transactions")
public class AdminTransactionServlet extends HttpServlet {

    @EJB
    private AdminTransactionBeanLocal adminTransactionBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Transaction> transactionList = adminTransactionBean.getAllTransactions();

        request.setAttribute("transactionList", transactionList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/transaction-log.jsp");
        dispatcher.forward(request, response);
    }
}