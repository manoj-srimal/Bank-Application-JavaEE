<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Dashboard - MyBank</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">


    <link rel="stylesheet" href="../css/style.css">
</head>
<body>

<div class="container-fluid">
    <div class="row">

        <nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
            <div class="position-sticky pt-3">
                <h4 class="sidebar-heading px-3 mt-2 mb-3 text-primary">MyBank</h4>
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/dashboard">
                            <i class="bi bi-grid-fill"></i> Dashboard
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="#history">
                            <i class="bi bi-arrow-down-up"></i> Transactions
                        </a>
                    </li>
                </ul>
                <hr>
                <div class="dropdown px-3">
                    <a href="#" class="d-flex align-items-center text-dark text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-circle fs-4 me-2"></i>
                        <strong>${sessionScope.user.firstName}</strong>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
                        <li><a class="dropdown-item" href="#">Profile</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logoutServlet">Sign out</a></li>
                    </ul>
                </div>
            </div>
        </nav>


        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Dashboard</h1>
            </div>


            <c:if test="${param.transfer == 'success'}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Success!</strong> Your fund transfer was completed successfully.
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            <c:if test="${param.schedule == 'success'}">
                <div class="alert alert-info alert-dismissible fade show" role="alert">
                    <strong>Success!</strong> Your future transfer has been scheduled successfully.
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            <c:if test="${not empty param.error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Error!</strong>
                    <c:choose>
                        <c:when test="${param.error == 'insufficient_balance'}">The transfer failed due to insufficient balance.</c:when>
                        <c:when test="${param.error == 'invalid_account'}">The recipient account number is invalid or does not exist.</c:when>
                        <c:when test="${param.error == 'no_account'}">You do not have a source account to transfer from.</c:when>
                        <c:when test="${param.error == 'schedule_failed'}">There was a problem scheduling your transfer.</c:when>
                        <c:otherwise>An unexpected error occurred. Please try again later.</c:otherwise>
                    </c:choose>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>


            <div class="row">
                <div class="col-lg-6 mb-4">
                    <div class="card text-white bg-primary shadow">
                        <div class="card-header">Primary Account</div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${not empty requestScope.accountList}">
                                    <c:set var="primaryAccount" value="${requestScope.accountList[0]}" />
                                    <h5 class="card-title">Current Balance</h5>
                                    <p class="card-text display-4"><fmt:formatNumber value="${primaryAccount.balance}" type="currency" currencySymbol="$ "/></p>
                                    <p class="card-text">Account No: ${primaryAccount.accountId}</p>
                                </c:when>
                                <c:otherwise>
                                    <h5 class="card-title">No Account Found</h5>
                                    <p class="card-text">An account is being automatically created for you.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>


            <div class="card mt-2 shadow-sm">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs" id="myTab" role="tablist">
                        <li class="nav-item" role="presentation"><button class="nav-link active" data-bs-toggle="tab" data-bs-target="#transfer" type="button">Fund Transfer</button></li>
                        <li class="nav-item" role="presentation"><button class="nav-link" data-bs-toggle="tab" data-bs-target="#schedule" type="button">Scheduled Transfer</button></li>
                        <li class="nav-item" role="presentation"><button class="nav-link" data-bs-toggle="tab" data-bs-target="#history" type="button">History</button></li>
                        <li class="nav-item" role="presentation"><button class="nav-link" data-bs-toggle="tab" data-bs-target="#profile" type="button">My Profile</button></li>
                    </ul>
                </div>
                <div class="card-body">
                    <div class="tab-content" id="myTabContent">


                        <div class="tab-pane fade show active p-3" id="transfer" role="tabpanel">
                            <h5 class="card-title">Instant Money Transfer</h5>
                            <form action="${pageContext.request.contextPath}/transferServlet" method="post" class="mt-3">
                                <div class="mb-3"><label for="recipientAccount" class="form-label">Recipient Account Number</label><input type="text" class="form-control" id="recipientAccount" name="recipientAccount" required></div>
                                <div class="mb-3"><label for="amount" class="form-label">Amount</label><input type="number" step="0.01" class="form-control" id="amount" name="amount" required></div>
                                <div class="mb-3"><label for="description" class="form-label">Description (Optional)</label><input type="text" class="form-control" id="description" name="description"></div>
                                <button type="submit" class="btn btn-primary">Transfer Now</button>
                            </form>
                        </div>


                        <div class="tab-pane fade p-3" id="schedule" role="tabpanel">
                            <h5 class="card-title">Schedule a Future Transfer</h5>
                            <form action="${pageContext.request.contextPath}/scheduleTransferServlet" method="post" class="mt-3">
                                <div class="mb-3"><label for="schRecipientAccount" class="form-label">Recipient Account Number</label><input type="text" class="form-control" id="schRecipientAccount" name="recipientAccount" required></div>
                                <div class="mb-3"><label for="schAmount" class="form-label">Amount</label><input type="number" step="0.01" class="form-control" id="schAmount" name="amount" required></div>
                                <div class="mb-3"><label for="schDate" class="form-label">Transfer Date</label><input type="date" class="form-control" id="schDate" name="transferDate" required></div>
                                <button type="submit" class="btn btn-success">Schedule Transfer</button>
                            </form>
                        </div>


                        <div class="tab-pane fade p-3" id="history" role="tabpanel">
                            <h5 class="card-title">Transaction History</h5>
                            <h6 class="mt-4">Recent Transactions</h6>
                            <div class="table-responsive"><table class="table table-striped table-sm"><thead><tr><th>Date</th><th>Description</th><th class="text-end">Amount</th></tr></thead><tbody>
                            <c:forEach var="tx" items="${requestScope.transactionHistory}">
                                <tr>
                                    <td><fmt:formatDate value="${tx.transactionTimestamp}" pattern="yyyy-MM-dd HH:mm"/></td>
                                    <td><c:choose><c:when test="${tx.fromAccount.accountId == primaryAccount.accountId}">Sent to ${tx.toAccount.accountId}</c:when><c:otherwise>Received from ${tx.fromAccount.accountId}</c:otherwise></c:choose><br/><small class="text-muted">${tx.description}</small></td>
                                    <td class="text-end"><c:choose><c:when test="${tx.fromAccount.accountId == primaryAccount.accountId}"><span class="text-danger fw-bold">- <fmt:formatNumber value="${tx.amount}" type="currency" currencySymbol="$ "/></span></c:when><c:otherwise><span class="text-success fw-bold">+ <fmt:formatNumber value="${tx.amount}" type="currency" currencySymbol="$ "/></span></c:otherwise></c:choose></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty requestScope.transactionHistory}"><tr><td colspan="3" class="text-center">No recent transactions found.</td></tr></c:if>
                            </tbody></table></div>
                            <h6 class="mt-5">Scheduled & Pending Transfers</h6>
                            <div class="table-responsive"><table class="table table-striped table-sm"><thead><tr><th>Scheduled For</th><th>Recipient</th><th class="text-end">Amount</th><th>Status</th></tr></thead><tbody>
                            <c:forEach var="task" items="${requestScope.scheduledTaskHistory}">
                                <tr>
                                    <td><fmt:formatDate value="${task.nextRunTime}" pattern="yyyy-MM-dd"/></td>
                                    <td>${task.destinationAccount.accountId}</td>
                                    <td class="text-end"><fmt:formatNumber value="${task.amount}" type="currency" currencySymbol="$ "/></td>
                                    <td><span class="badge <c:choose><c:when test='${task.status == "COMPLETED"}'>bg-success</c:when><c:when test='${task.status == "PENDING"}'>bg-warning</c:when><c:when test='${task.status == "FAILED"}'>bg-danger</c:when><c:otherwise>bg-secondary</c:otherwise></c:choose>">${task.status}</span></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty requestScope.scheduledTaskHistory}"><tr><td colspan="4" class="text-center">No scheduled transfers found.</td></tr></c:if>
                            </tbody></table></div>
                        </div>

                        <div class="tab-pane fade p-3" id="profile" role="tabpanel">
                            <h5 class="card-title">Your Profile Details</h5>
                            <ul class="list-group list-group-flush mt-3">
                                <li class="list-group-item"><strong>First Name:</strong> ${sessionScope.user.firstName}</li>
                                <li class="list-group-item"><strong>Last Name:</strong> ${sessionScope.user.lastName}</li>
                                <li class="list-group-item"><strong>Email:</strong> ${sessionScope.user.email}</li>
                                <li class="list-group-item"><strong>Phone Number:</strong> ${sessionScope.user.phoneNumber}</li>
                                <li class="list-group-item"><strong>Status:</strong> <span class="badge bg-success">${sessionScope.user.status}</span></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>