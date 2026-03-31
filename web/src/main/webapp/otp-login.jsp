<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enter OTP - MyBank</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container register-container">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-5">
            <div class="card register-card shadow-lg">
                <div class="card-body p-5">
                    <div class="text-center mb-4">
                        <h2 class="card-title">Two-Factor Authentication</h2>
                        <p class="card-subtitle text-muted">Please enter the 6-digit code sent to your email.</p>
                    </div>

                    <form action="verifyLoginOtpServlet" method="post">
                        <div class="mb-3">
                            <label for="otp" class="form-label">One-Time Password (OTP)</label>
                            <input type="text" class="form-control text-center fs-4"
                                   id="otp" name="otp"
                                   maxlength="6"
                                   required
                                   autofocus
                                   oninput="this.value = this.value.replace(/[^0-9.]/g, '');">
                        </div>

                        <%-- Display error message if OTP is invalid --%>
                        <% if ("invalidotp".equals(request.getParameter("error"))) { %>
                        <div class="alert alert-danger" role="alert">
                            Invalid or expired OTP. Please try again.
                        </div>
                        <% } %>

                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-primary btn-lg register-btn">Verify & Login</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>