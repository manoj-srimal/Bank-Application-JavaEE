<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="si">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Successful - MyBank</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container register-container">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-6">
            <div class="card register-card shadow-lg text-center">
                <div class="card-body p-5">

                    <div class="mb-4">
                        <svg xmlns="http://www.w3.org/2000/svg" width="80" height="80" fill="currentColor" class="bi bi-envelope-check-fill text-success" viewBox="0 0 16 16">
                            <path d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414zM0 4.697v7.104l5.803-3.558zM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586zm3.436-.586L16 11.801V4.697z"/>
                            <path d="M16 12.5a3.5 3.5 0 1 1-7 0 3.5 3.5 0 0 1 7 0m-1.993-1.679a.5.5 0 0 0-.686.172l-1.17 1.95-.547-.547a.5.5 0 0 0-.708.708l.793.793a.5.5 0 0 0 .707 0l1.5-2.5a.5.5 0 0 0-.172-.686"/>
                        </svg>
                    </div>

                    <h2 class="card-title">Registration Successful</h2>
                    <p class="card-text fs-5 mt-3">
                        To confirm your account, we have sent a link to your email address.
                    </p>
                    <p class="text-muted mt-4">
                        Please check your inbox for verification...
                    </p>
                    <div class="d-grid mt-4">
                        <a href="index.jsp" class="btn btn-outline-primary">Login</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>