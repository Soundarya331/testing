<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="header.jsp" %>

<div class="container mt-4">
    <h2>Agent Dashboard</h2>
    <p>Use the links below to manage policies.</p>
    <ul>
        <li><a href="listPolicies" class="btn btn-link">View All Policies</a></li>
        <li><a href="policyForm" class="btn btn-link">Create New Policy</a></li>
        <li><a href="adminPurchases" class="btn btn-link">View Purchases</a></li>
    </ul>
</div>

<%@ include file="footer.jsp" %>