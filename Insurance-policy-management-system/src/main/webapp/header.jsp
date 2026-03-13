<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Insurance Policy Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">InsurancePMS</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" href="agentDashboard.jsp">Agent Dashboard</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="customerDashboard.jsp">Customer Dashboard</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="policies.jsp">All Policies</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="browsePolicies.jsp">Browse Policies</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="myPolicies.jsp">My Policies</a>
        </li>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link text-light" href="#">Welcome, <%= session.getAttribute("userName") != null ? session.getAttribute("userName") : "Guest" %></a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="logout">Logout</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
<div class="container mt-4">