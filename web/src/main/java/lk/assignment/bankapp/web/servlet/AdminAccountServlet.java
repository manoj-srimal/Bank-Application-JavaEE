package lk.assignment.bankapp.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.assignment.bankapp.core.model.Account;
import lk.assignment.bankapp.core.service.AdminAccountManagementBeanLocal;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminAccountServlet", value = "/admin/accounts")
public class AdminAccountServlet extends HttpServlet {

    @EJB
    private AdminAccountManagementBeanLocal adminAccountBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Account> allAccountsList = adminAccountBean.getAllBankAccounts();

        request.setAttribute("allAccountsList", allAccountsList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/account-management.jsp");
        dispatcher.forward(request, response);
    }
}
