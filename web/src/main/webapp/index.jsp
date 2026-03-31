<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="si">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MyBank - LogIn</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container register-container">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-5">
            <div class="card register-card shadow-lg">
                <div class="card-body p-5">
                    <div class="text-center mb-4">
                        <h2 class="card-title">Welcome Back!</h2>
                        <p class="card-subtitle text-muted">Please login to your account</p>
                    </div>

                    <form action="loginServlet" method="post">
                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>

                        <div class="d-flex justify-content-end mb-4">
                            <a href="forgot-password.jsp">Forgot Password?</a>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-lg register-btn">Login</button>
                        </div>
                    </form>

                    <div class="text-center mt-4">
                        <p class="text-muted">Don't have an account? <a href="register.jsp">Register Now</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>