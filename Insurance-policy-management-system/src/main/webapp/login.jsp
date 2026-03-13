<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <h2 class="text-center mb-4">Login Form</h2>

            <div id="alertMsg" class="alert d-none"></div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="text" id="email" class="form-control" placeholder="Enter email"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Password</label>
                <input type="password" id="password" class="form-control" placeholder="Enter password"/>
            </div>
            <button type="button" id="btnLogin" class="btn btn-primary w-100">
                <span id="btnText">Login</span>
                <span id="btnSpinner" class="spinner-border spinner-border-sm d-none ms-2"></span>
            </button>
        </div>
    </div>
</div>

<script>
$(function () {

    $('#btnLogin').on('click', function () {
        var email    = $('#email').val().trim();
        var password = $('#password').val().trim();

        if (!email || !password) {
            showAlert('danger', 'Please enter both email and password.');
            return;
        }

        $('#btnText').text('Signing in...');
        $('#btnSpinner').removeClass('d-none');
        $('#btnLogin').prop('disabled', true);
        $('#alertMsg').addClass('d-none');

        $.ajax({
            url:      'loginAction',   /* removed .action suffix */
            type:     'POST',
            data: {
                email:    email,
                password: password
            },
            success: function (response) {
                resetBtn();

                /* Struts may return a string or object — handle both */
                var res = (typeof response === 'string') ? JSON.parse(response) : response;

                if (res.status === 'success') {
                    showAlert('success',
                        'Login successful! Role: <strong>' + res.role + '</strong>. Redirecting...');
                    setTimeout(function () {
                        window.location.href = res.redirect + '.action';
                    }, 1000);
                } else {
                    showAlert('danger', res.message || 'Invalid credentials. Please try again.');
                }
            },
            error: function (xhr) {
                resetBtn();
                /*
                 * Try to read JSON from error response body.
                 * Struts sends JSON even for ERROR result if json-plugin is configured.
                 */
                try {
                    var res = JSON.parse(xhr.responseText);
                    showAlert('danger', res.message || 'Invalid credentials. Please try again.');
                } catch (e) {
                    /* Last fallback — show HTTP status, not generic message */
                    showAlert('danger', 'Login failed (HTTP ' + xhr.status + '). Check server logs.');
                }
            }
        });
    });

    $('#email, #password').on('keydown', function (e) {
        if (e.key === 'Enter') $('#btnLogin').trigger('click');
    });

    function showAlert(type, msg) {
        $('#alertMsg')
            .removeClass('d-none alert-success alert-danger')
            .addClass('alert-' + type)
            .html(msg);
    }

    function resetBtn() {
        $('#btnText').text('Login');
        $('#btnSpinner').addClass('d-none');
        $('#btnLogin').prop('disabled', false);
    }

});
</script>
</body>
</html>
