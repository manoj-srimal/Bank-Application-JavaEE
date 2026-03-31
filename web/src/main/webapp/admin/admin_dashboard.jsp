<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - MyBank</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <%-- Path to CSS is relative to the webapp root, going up one level from the 'admin' folder --%>
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
                    <%-- All links now point to the correct servlets --%>
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/admin/dashboard"><i class="bi bi-speedometer2"></i> Overview</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/users"><i class="bi bi-people-fill"></i> User Management</a></li>
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
                <h1 class="h2">Admin Overview</h1>
            </div>

            <!-- Stat Cards with Dynamic Data -->
            <div class="row">
                <div class="col-md-4">
                    <div class="card text-white bg-info mb-3">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h5 class="card-title">Total Users</h5>
                                    <p class="card-text fs-2">${requestScope.userCount}</p>
                                </div>
                                <i class="bi bi-people-fill fs-1"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-white bg-success mb-3">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h5 class="card-title">Total Accounts</h5>
                                    <p class="card-text fs-2">${requestScope.accountCount}</p>
                                </div>
                                <i class="bi bi-journal-album fs-1"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-white bg-warning mb-3">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h5 class="card-title">Pending Tasks</h5>
                                    <p class="card-text fs-2">0</p>
                                </div>
                                <i class="bi bi-clock-history fs-1"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- User Management Table with Dynamic Data -->
            <div class="card mt-4">
                <div class="card-header">
                    <h4><i class="bi bi-people-fill"></i> User Management</h4>
                </div>
                <div class="card-body">
                    <p>Here you can view and manage all registered users.</p>
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                            <tr><th>User ID</th><th>Name</th><th>Email</th><th>Status</th><th>Actions</th></tr>
                            </thead>
                            <tbody>
                            <c:forEach var="u" items="${requestScope.userList}">
                                <tr>
                                    <td>${u.userId}</td>
                                    <td>${u.firstName} ${u.lastName}</td>
                                    <td>${u.email}</td>
                                    <td>
                                        <span class="badge ${u.status == 'ACTIVE' ? 'bg-success' : 'bg-secondary'}">${u.status}</span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/users?action=toggleStatus&userId=${u.userId}"
                                           class="btn btn-sm ${u.status == 'ACTIVE' ? 'btn-outline-danger' : 'btn-outline-success'}">
                                                ${u.status == 'ACTIVE' ? 'Deactivate' : 'Activate'}
                                        </a>
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
