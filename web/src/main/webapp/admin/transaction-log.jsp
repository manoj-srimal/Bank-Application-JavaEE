<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Transaction Log - MyBank Admin</title>
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
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard"><i class="bi bi-speedometer2"></i> Overview</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/users"><i class="bi bi-people-fill"></i> User Management</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/accounts"><i class="bi bi-journal-album"></i> Account Management</a></li>
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/admin/transactions"><i class="bi bi-arrow-down-up"></i> Transaction Log</a></li>
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
                <h1 class="h2">System Transaction Log</h1>
            </div>

            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                            <tr>
                                <th>Date & Time</th>
                                <th>Type</th>
                                <th>From Account</th>
                                <th>To Account</th>
                                <th class="text-end">Amount</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="tx" items="${requestScope.transactionList}">
                                <tr>
                                    <td><fmt:formatDate value="${tx.transactionTimestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>
                                            <span class="badge
                                                <c:choose>
                                                    <c:when test='${tx.transactionType == "TRANSFER"}'>bg-primary</c:when>
                                                    <c:when test='${tx.transactionType == "INTEREST"}'>bg-info</c:when>
                                                    <c:otherwise>bg-secondary</c:otherwise>
                                                </c:choose>
                                            ">${tx.transactionType}</span>
                                    </td>
                                    <td>${not empty tx.fromAccount ? tx.fromAccount.accountId : 'N/A'}</td>
                                    <td>${not empty tx.toAccount ? tx.toAccount.accountId : 'N/A'}</td>
                                    <td class="text-end fw-bold">
                                        <fmt:formatNumber value="${tx.amount}" type="currency" currencySymbol="$ "/>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty requestScope.transactionList}">
                                <tr><td colspan="5" class="text-center">No transactions found in the system.</td></tr>
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
