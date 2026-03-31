<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Verification Failed</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container register-container">
  <div class="row justify-content-center">
    <div class="col-md-7 col-lg-6">
      <div class="card register-card shadow-lg text-center">
        <div class="card-body p-5">
          <div class="mb-4">
            <svg xmlns="http://www.w3.org/2000/svg" width="80" height="80" fill="currentColor" class="bi bi-x-circle-fill text-danger" viewBox="0 0 16 16">
              <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0M5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293z"/>
            </svg>
          </div>
          <h2 class="card-title">Verification Failed!</h2>
          <p class="card-text fs-5 mt-3">
            The verification link is invalid or has expired.
          </p>
          <div class="d-grid mt-4">
            <a href="register.jsp" class="btn btn-outline-primary">Try Registering Again</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>