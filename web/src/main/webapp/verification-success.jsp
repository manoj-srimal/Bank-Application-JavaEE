<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account Verified!</title>
    <meta http-equiv="refresh" content="5;url=index.jsp">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <style>
        .icon-success {
            font-size: 80px;
            color: #198754;
        }
    </style>
</head>
<body>
<div class="container register-container">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-6">
            <div class="card register-card shadow-lg text-center">
                <div class="card-body p-5">
                    <div class="mb-4">
                        <svg xmlns="http://www.w3.org/2000/svg" width="80" height="80" fill="currentColor" class="bi bi-check-circle-fill text-success" viewBox="0 0 16 16">
                            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0m-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
                        </svg>
                    </div>
                    <h2 class="card-title">Verification Successful!</h2>
                    <p class="card-text fs-5 mt-3">
                        Your account has been successfully verified.
                    </p>
                    <p class="text-muted mt-4">
                        You will be redirected to the login page in 5 seconds.
                    </p>
                    <div class="d-grid mt-4">
                        <a href="index.jsp" class="btn btn-primary">Login Now</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
