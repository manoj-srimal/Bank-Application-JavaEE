package lk.assignment.bankapp.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.assignment.bankapp.core.service.UserManagementBeanLocal;

import java.io.IOException;

@WebServlet(name = "VerifyServlet", value = "/verify")
public class VerifyServlet extends HttpServlet {

    @EJB
    private UserManagementBeanLocal userManagementBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the verification code from the URL parameter (?vc=...)
        String verificationCode = request.getParameter("vc");

        if (verificationCode != null && !verificationCode.isEmpty()) {
            boolean isVerified = userManagementBean.verifyUserAccount(verificationCode);

            if (isVerified) {
                // If verification is successful, forward to the success page
                RequestDispatcher dispatcher = request.getRequestDispatcher("verification-success.jsp");
                dispatcher.forward(request, response);
            } else {
                // If verification fails (invalid code), forward to the failure page
                RequestDispatcher dispatcher = request.getRequestDispatcher("verification-failure.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            // If no code is provided in the URL, show failure page as well
            RequestDispatcher dispatcher = request.getRequestDispatcher("verification-failure.jsp");
            dispatcher.forward(request, response);
        }
    }
}
