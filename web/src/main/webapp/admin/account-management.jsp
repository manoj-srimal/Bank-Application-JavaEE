<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="lk.assignment.bankapp.core.model.User, lk.assignment.bankapp.core.model.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
            <div class="position-sticky pt-3">
                <h4 class="sidebar-heading px-3 mt-2 mb-3 text-primary">MyBank - Admin</h4>
                <ul class="nav flex-column">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard"><i class="bi bi-speedometer2"></i> Overview</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/users"><i class="bi bi-people-fill"></i> User Management</a></li>
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/admin/accounts"><i class="bi bi-journal-album"></i> Account Management</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/transactions"><i class="bi bi-arrow-down-up"></i> Transaction Log</a></li>
                </ul>
            </div>
        </nav>

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Account Management</h1>
            </div>

            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                            <tr>
                                <th>Account No.</th>
                                <th>Account Holder</th>
                                <th>Type</th>
                                <th class="text-end">Balance</th>
                                <th>Status</th>
                                <th>Opened On</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="account" items="${allAccountsList}">
                                <tr>
                                    <td>${account.accountId}</td>
                                    <td>${account.user.firstName} ${account.user.lastName}</td>
                                    <td>
                                        <span class="badge bg-info-subtle text-info-emphasis">${account.accountType}</span>
                                    </td>
                                    <td class="text-end fw-bold">
                                        <fmt:formatNumber value="${account.balance}" type="currency" currencySymbol="$ "/>
                                    </td>
                                    <td>
                                        <span class="badge ${account.status == 'ACTIVE' ? 'bg-success' : 'bg-danger'}">${account.status}</span>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${account.openedAt}" pattern="yyyy-MM-dd"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty allAccountsList}">
                                <tr><td colspan="6" class="text-center">No accounts found in the system.</td></tr>
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