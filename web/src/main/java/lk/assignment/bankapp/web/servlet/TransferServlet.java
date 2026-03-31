package lk.assignment.bankapp.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.assignment.bankapp.core.exception.InsufficientBalanceException;
import lk.assignment.bankapp.core.exception.InvalidAccountException;
import lk.assignment.bankapp.core.model.Account;
import lk.assignment.bankapp.core.model.User;
import lk.assignment.bankapp.core.service.AccountManagementBeanLocal;
import lk.assignment.bankapp.core.service.FundTransferBeanLocal;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "TransferServlet", value = "/transferServlet")
public class TransferServlet extends HttpServlet {

    @EJB
    private AccountManagementBeanLocal accountBean;

    @EJB
    private FundTransferBeanLocal fundTransferBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String recipientAccount = request.getParameter("recipientAccount");
        String description = request.getParameter("description");
        double amount = Double.parseDouble(request.getParameter("amount"));

        List<Account> userAccounts = accountBean.getAccountsForUser(user.getUserId());
        if (userAccounts.isEmpty()) {
            response.sendRedirect("dashboard?error=no_account");
            return;
        }
        String fromAccount = userAccounts.get(0).getAccountId();

        try {

            fundTransferBean.performTransfer(user.getUserId(), fromAccount, recipientAccount, amount, description);

            response.sendRedirect("dashboard?transfer=success");

        } catch (InsufficientBalanceException e) {
            response.sendRedirect("dashboard?error=insufficient_balance");
        } catch (InvalidAccountException e) {
            response.sendRedirect("dashboard?error=invalid_account");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("dashboard?error=unknown");
        }
    }
}
