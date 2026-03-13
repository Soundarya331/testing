<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Login - InsurancePMS</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet" crossorigin="anonymous">
</head>
<body class="d-flex align-items-center" style="min-height: 100vh;">
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-4">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Login</h4>
						<form id="login-form">
							<div class="mb-3">
								<label class="form-label">Email</label> <input type="email"
									id="email" name="email" class="form-control" required />
							</div>
							<div class="mb-3">
								<label class="form-label">Password</label> <input
									type="password" id="password" name="password"
									class="form-control" required />
							</div>
							<button class="btn btn-primary w-100" type="button"
								onclick="doLogin()">Login</button>
						</form>
						<div id="login-message" class="mt-3">
							<s:if test="hasActionErrors()">
								<div class="alert alert-danger">
									<s:actionerror />
								</div>
							</s:if>
						</div>
						<div><a href="register.jsp">Register</a></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script>
        const ctx = "${pageContext.request.contextPath}";

        function doLogin() {
            const email    = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            if (!email || !password) {
                document.getElementById("login-message").innerHTML =
                    `<div class='alert alert-danger'>Email and password are required.</div>`;
                return;
            }

            fetch(ctx + "/login.action", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: "email=" + encodeURIComponent(email) +
                      "&password=" + encodeURIComponent(password)
            })
            .then(response => response.json())
            .then(data => {
    const messageDiv = document.getElementById("login-message");
    if (data.actionErrors && data.actionErrors.length > 0) {
        messageDiv.innerHTML =
            `<div class='alert alert-danger'>${data.actionErrors[0]}</div>`;
    } else if (data.role === "AGENT") {
        messageDiv.innerHTML =
            `<div class='alert alert-success'>Welcome Admin! Redirecting...</div>`;
        setTimeout(() => window.location.href =
            ctx + "/agentDashboard.action", 1500);
    } else if (data.role === "CUSTOMER") {
        messageDiv.innerHTML =
            `<div class='alert alert-success'>Welcome! Redirecting...</div>`;
        setTimeout(() => window.location.href =
            ctx + "/customerDashboard.action", 1500);
    } else {
        messageDiv.innerHTML =
            `<div class='alert alert-danger'>Invalid email or password.</div>`;
    }
})
.catch(error => {
    console.error("Error:", error);
    document.getElementById("login-message").innerHTML =
        `<div class='alert alert-danger'>An error occurred: ${error.message}</div>`;
});
        }
    </script>
</body>
</html>