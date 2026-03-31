package lk.assignment.bankapp.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.assignment.bankapp.core.model.User;
import lk.assignment.bankapp.core.service.AdminUserManagementBeanLocal;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminUserServlet", value = "/admin/users")
public class AdminUserServlet extends HttpServlet {

    @EJB
    private AdminUserManagementBeanLocal adminUserBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        HttpSession session = request.getSession(false);
        User adminUser = (User) session.getAttribute("user");

        try {

            switch (action) {
                case "toggleStatus":
                    Long userIdToToggle = Long.parseLong(request.getParameter("userId"));
                    adminUserBean.toggleUserStatus(userIdToToggle, adminUser.getUserId());

                    response.sendRedirect(request.getContextPath() + "/admin/users");
                    break;

                case "list":
                default:

                    List<User> userList = adminUserBean.getAllUsers();

                    request.setAttribute("userList", userList);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/user-management.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();

            response.sendRedirect(request.getContextPath() + "/admin/users?error=true");
        }
    }
}