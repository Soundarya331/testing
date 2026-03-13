<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Registration</title>
</head>
<body>
<h2>Customer Registration</h2>
<form action="${pageContext.request.contextPath}/register" method="post">
    <label for="name">Name:</label><br/>
    <input type="text" id="name" name="name" required/><br/>

    <label for="age">Age:</label><br/>
    <input type="number" id="age" name="age" min="0" required/><br/>

    <label for="email">Email:</label><br/>
    <input type="email" id="email" name="email" required/><br/>

    <br/>
    <button type="submit">Register</button>
</form>

</body>
</html>