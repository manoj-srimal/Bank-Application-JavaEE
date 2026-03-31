package lk.assignment.bankapp.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.assignment.bankapp.core.model.Account;
import lk.assignment.bankapp.core.model.ScheduledTask;
import lk.assignment.bankapp.core.model.Transaction;
import lk.assignment.bankapp.core.model.User;
import lk.assignment.bankapp.core.service.AccountManagementBeanLocal;
import lk.assignment.bankapp.core.service.FundTransferBeanLocal;
import lk.assignment.bankapp.core.service.ScheduledTransferBeanLocal;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class DashboardServlet extends HttpServlet {

    @EJB private AccountManagementBeanLocal accountBean;
    @EJB private FundTransferBeanLocal fundTransferBean;
    @EJB private ScheduledTransferBeanLocal scheduledTransferBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        List<Account> accountList = accountBean.getAccountsForUser(user.getUserId());
        request.setAttribute("accountList", accountList);

        if (accountList != null && !accountList.isEmpty()) {
            String primaryAccountId = accountList.get(0).getAccountId();

            List<Transaction> transactionHistory = fundTransferBean.getTransactionHistory(primaryAccountId);
            request.setAttribute("transactionHistory", transactionHistory);

            List<ScheduledTask> scheduledTaskHistory = scheduledTransferBean.getScheduledTaskHistory(primaryAccountId);
            request.setAttribute("scheduledTaskHistory", scheduledTaskHistory);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/user_dashboard.jsp");
        dispatcher.forward(request, response);
    }
}