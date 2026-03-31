package lk.assignment.bankapp.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.assignment.bankapp.core.model.User;
import lk.assignment.bankapp.core.service.UserManagementBeanLocal;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/registerServlet")
public class RegisterServlet extends HttpServlet {

    @EJB
    private UserManagementBeanLocal userManagementBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");


        if (password == null || !password.equals(confirmPassword)) {

            response.sendRedirect("register.jsp?error=passwordmismatch");
            return;
        }


        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);

        newUser.setPasswordHash(password);

        try {
            userManagementBean.registerUser(newUser);

            response.sendRedirect("registration-pending.jsp");

        } catch (Exception e) {
            e.printStackTrace();

            // Check if the error is due to a duplicate email
            if (e.getMessage().contains("already exists")) {
                response.sendRedirect("register.jsp?error=emailtaken");
            } else {
                response.sendRedirect("register.jsp?error=unknown");
            }
        }
    }
}
