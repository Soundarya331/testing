<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<h2>Customer Dashboard</h2>
<p>Welcome to your dashboard. You can browse and purchase policies below.</p>

<div id="dashboard-content">
  <ul>
    <li><a href="browsePolicies.jsp" class="btn btn-link">Browse Policies</a></li>
    <li><a href="myPolicies.jsp" class="btn btn-link">View My Policies</a></li>
  </ul>
</div>

<script>
  document.addEventListener("DOMContentLoaded", function() {
    fetch("/api/customerDashboard") // Replace with the actual backend endpoint
      .then(response => {
        if (!response.ok) {
          throw new Error("Failed to fetch dashboard content");
        }
        return response.json();
      })
      .then(data => {
        const dashboardContent = document.getElementById("dashboard-content");
        dashboardContent.innerHTML = data.htmlContent; // Assuming the backend returns HTML content
      })
      .catch(error => {
        console.error("Error loading dashboard content:", error);
      });
  });
</script>

<%@ include file="footer.jsp" %>