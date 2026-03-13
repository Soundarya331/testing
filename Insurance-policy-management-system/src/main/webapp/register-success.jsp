<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Successful</title>
</head>
<body>
<h2>Registration Successful</h2>
<p>Thank you, ${sessionScope.userName} (ID: ${sessionScope.customerId}), for registering.</p>
<p><a href="${pageContext.request.contextPath}/register.jsp">Register another</a></p>
</body>
</html>