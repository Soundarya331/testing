<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Customer Registration - InsurancePMS</title>


<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">


</head>

<body class="bg-light d-flex align-items-center" style="min-height:100vh;">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">


        <div class="card shadow">
            <div class="card-body">

                <h4 class="card-title text-center mb-4">Customer Registration</h4>

                <form id="register-form">

                    <div class="mb-3">
                        <label class="form-label">Customer Name</label>
                        <input type="text" name="customerName" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Password</label>
                        <input type="password" name="password" class="form-control" required minlength="6">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Phone Number</label>
                        <input type="tel" name="phoneNumber" class="form-control" pattern="[0-9]{10}" placeholder="10 digit phone number" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Date of Birth</label>
                        <input type="date" name="dateOfBirth" class="form-control" required>
                    </div>

                    <!-- role automatically CUSTOMER -->
                    <input type="hidden" name="role" value="CUSTOMER">

                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">
                            Register
                        </button>
                    </div>

                </form>

                <div id="register-message" class="mt-3"></div>

                <div class="text-center mt-3">
                    <a href="login.jsp">Already have an account? Login</a>
                </div>

            </div>
        </div>

    </div>
</div>
```

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script>

document.getElementById("register-form").addEventListener("submit", function(event){

    event.preventDefault();

    const formData = new FormData(event.target);

    fetch("/api/register", {

        method: "POST",

        headers:{
            "Content-Type":"application/json"
        },

        body: JSON.stringify({
            customerName: formData.get("customerName"),
            email: formData.get("email"),
            password: formData.get("password"),
            phoneNumber: formData.get("phoneNumber"),
            dateOfBirth: formData.get("dateOfBirth"),
            role: formData.get("role")
        })

    })

    .then(response => response.json())

    .then(data => {

        const messageDiv = document.getElementById("register-message");

        if(data.success){

            messageDiv.innerHTML =
            `<div class="alert alert-success">Registration successful! Redirecting to login...</div>`;

            setTimeout(()=>{
                window.location.href="login.jsp";
            },2000);

        }else{

            messageDiv.innerHTML =
            `<div class="alert alert-danger">${data.message}</div>`;

        }

    })

    .catch(error=>{

        document.getElementById("register-message").innerHTML =
        `<div class="alert alert-danger">Registration failed. Please try again.</div>`;

    });

});

</script>

</body>
</html>