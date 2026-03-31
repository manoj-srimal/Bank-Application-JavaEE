package lk.assignment.bankapp.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.assignment.bankapp.core.model.User;
import lk.assignment.bankapp.core.service.AdminUserManagementBeanLocal;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminDashboardServlet", value = "/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    @EJB
    private AdminUserManagementBeanLocal adminUserBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long userCount = adminUserBean.getTotalUserCount();
        long accountCount = adminUserBean.getTotalAccountCount();
        List<User> userList = adminUserBean.getAllUsers();

        request.setAttribute("userCount", userCount);
        request.setAttribute("accountCount", accountCount);
        request.setAttribute("userList", userList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_dashboard.jsp");
        dispatcher.forward(request, response);
    }
}
