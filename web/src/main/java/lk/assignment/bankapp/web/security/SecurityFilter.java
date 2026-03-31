package lk.assignment.bankapp.web.security;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.assignment.bankapp.core.model.User;

import java.io.IOException;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        String path = request.getRequestURI().substring(request.getContextPath().length());

        if (path.startsWith("/css/") ||
                path.startsWith("/images/") ||
                path.equals("/index.jsp") ||
                path.equals("/") ||
                path.equals("/register.jsp") ||
                path.equals("/loginServlet") ||
                path.equals("/registerServlet") ||
                path.equals("/verify") ||
                path.equals("/otp-login.jsp") ||
                path.equals("/verifyLoginOtpServlet") ||
                path.equals("/unauthorized.jsp") ||
                path.equals("/error.jsp") ||
                path.equals("/login_error.jsp") ||
                path.equals("/registration-pending.jsp") ||
                path.equals("/verification-success.jsp") ||
                path.equals("/verification-failure.jsp")) {

            filterChain.doFilter(request, response);
            return;
        }

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=pleaselogin");
            return;
        }

        boolean isAdmin = user.getRoles().stream().anyMatch(role -> "ADMIN".equals(role.getRoleName()));

        if (path.startsWith("/admin/")) {
            if (isAdmin) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/unauthorized.jsp");
            }
            return;
        }

        if (path.startsWith("/user/") || path.equals("/dashboard") || path.equals("/transferServlet") || path.equals("/scheduleTransferServlet")) {
            if (!isAdmin) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            }
            return;
        }

        filterChain.doFilter(request, response);
    }

}