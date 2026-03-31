package lk.assignment.bankapp.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.assignment.bankapp.core.model.User;
import lk.assignment.bankapp.core.service.UserManagementBeanLocal;
import java.io.IOException;

@WebServlet(name = "VerifyLoginOtpServlet", value = "/verifyLoginOtpServlet")
public class VerifyLoginOtpServlet extends HttpServlet {

    @EJB
    private UserManagementBeanLocal userManagementBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("--- VerifyLoginOtpServlet: Reached the servlet. ---");
        HttpSession session = request.getSession(false);

        if (session != null) {
            System.out.println("VerifyServlet: Session FOUND. ID = " + session.getId());
            Object userIdObj = session.getAttribute("pending_login_userid");
            System.out.println("VerifyServlet: 'pending_login_userid' attribute value = " + userIdObj);
        } else {
            System.out.println("VerifyServlet: Session is NULL.");
        }


        String otp = request.getParameter("otp");

        if (session == null || session.getAttribute("pending_login_userid") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=sessionexpired");
            return;
        }

        Long userId = (Long) session.getAttribute("pending_login_userid");
        User user = userManagementBean.validateLoginOtp(userId, otp);

        if (user != null) {
            session.removeAttribute("pending_login_userid");
            session.setAttribute("user", user);

            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {

            response.sendRedirect("otp-login.jsp?error=invalidotp");
        }
    }
}