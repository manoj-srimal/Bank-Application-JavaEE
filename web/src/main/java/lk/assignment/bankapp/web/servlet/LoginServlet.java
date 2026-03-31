package lk.assignment.bankapp.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.assignment.bankapp.core.model.Role;
import lk.assignment.bankapp.core.model.User;
import lk.assignment.bankapp.core.service.UserManagementBeanLocal;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {

    @EJB
    private UserManagementBeanLocal userManagementBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userManagementBean.loginUser(email, password);

        if (user != null) {

            boolean isAdmin = false;
            for (Role role : user.getRoles()) {
                if ("ADMIN".equals(role.getRoleName())) {
                    isAdmin = true;
                    break;
                }
            }

            HttpSession session = request.getSession();

            if (isAdmin) {
                userManagementBean.createAndSendLoginOtp(user);

                session.setAttribute("pending_login_userid", user.getUserId());
                System.out.println("DEBUG (LoginServlet): Session created. ID = " + session.getId() + " | UserID set = " + user.getUserId());
                response.sendRedirect("otp-login.jsp");

            } else {
                session.setAttribute("user", user);

                response.sendRedirect("dashboard");
            }

        } else {
            response.sendRedirect("index.jsp?error=invalid");
        }
    }
}
