<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
    Unnecessary page import directives have been removed as we are no longer using scriptlets.
    Security is handled by SecurityFilter.java.
--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Management - MyBank Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar Navigation -->
        <nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
            <div class="position-sticky pt-3">
                <h4 class="sidebar-heading px-3 mt-2 mb-3 text-primary">MyBank - Admin</h4>
                <ul class="nav flex-column">
                    <%-- All links point to their respective servlets for data loading --%>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard"><i class="bi bi-speedometer2"></i> Overview</a></li>
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/admin/users"><i class="bi bi-people-fill"></i> User Management</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/accounts"><i class="bi bi-journal-album"></i> Account Management</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/transactions"><i class="bi bi-arrow-down-up"></i> Transaction Log</a></li>
                </ul>
                <hr>
                <div class="dropdown px-3">
                    <a href="#" class="d-flex align-items-center text-dark text-decoration-none dropdown-toggle" data-bs-toggle="dropdown">
                        <i class="bi bi-person-circle fs-4 me-2"></i>
                        <strong>${sessionScope.user.firstName}</strong>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logoutServlet">Sign out</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <!-- Main Content -->
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">User Management</h1>
            </div>

            <c:if test="${param.error == 'true'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Error!</strong> An error occurred while updating the user status.
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                            <tr>
                                <th>User ID</th>
                                <th>Full Name</th>
                                <th>Email</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="user" items="${requestScope.userList}">
                                <tr>
                                    <td>${user.userId}</td>
                                    <td>${user.firstName} ${user.lastName}</td>
                                    <td>${user.email}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.status == 'ACTIVE'}">
                                                <span class="badge bg-success">ACTIVE</span>
                                            </c:when>
                                            <c:when test="${user.status == 'INACTIVE'}">
                                                <span class="badge bg-secondary">INACTIVE</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning text-dark">PENDING</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                            <%-- The relative link 'users?action=...' is correct here --%>
                                        <c:choose>
                                            <c:when test="${user.status == 'ACTIVE'}">
                                                <a href="users?action=toggleStatus&userId=${user.userId}" class="btn btn-sm btn-outline-danger">Deactivate</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="users?action=toggleStatus&userId=${user.userId}" class="btn btn-sm btn-outline-success">Activate</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty requestScope.userList}">
                                <tr><td colspan="5" class="text-center">No non-admin users found.</td></tr>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
