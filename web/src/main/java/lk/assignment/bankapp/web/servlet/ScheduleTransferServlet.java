package lk.assignment.bankapp.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.assignment.bankapp.core.exception.InvalidAccountException;
import lk.assignment.bankapp.core.model.Account;
import lk.assignment.bankapp.core.model.User;
import lk.assignment.bankapp.core.service.AccountManagementBeanLocal;
import lk.assignment.bankapp.core.service.ScheduledTransferBeanLocal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ScheduleTransferServlet", value = "/scheduleTransferServlet")
public class ScheduleTransferServlet extends HttpServlet {

    @EJB
    private ScheduledTransferBeanLocal scheduledTransferBean;

    @EJB
    private AccountManagementBeanLocal accountBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String recipientAccount = request.getParameter("recipientAccount");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String transferDateStr = request.getParameter("transferDate");

        // Get the user's source account
        List<Account> userAccounts = accountBean.getAccountsForUser(user.getUserId());
        if (userAccounts.isEmpty()) {
            response.sendRedirect("dashboard?error=no_account");
            return;
        }
        String fromAccount = userAccounts.get(0).getAccountId();

        try {
            // Convert date string to Date object
            Date transferDate = new SimpleDateFormat("yyyy-MM-dd").parse(transferDateStr);

            // Call the EJB to create the scheduled task
            scheduledTransferBean.createScheduledTransfer(user.getUserId(), fromAccount, recipientAccount, amount, transferDate);

            response.sendRedirect("dashboard?schedule=success");

        } catch (InvalidAccountException e) {
            response.sendRedirect("dashboard?error=invalid_account");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("dashboard?error=schedule_failed");
        }
    }
}
