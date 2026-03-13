<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<h2>Error</h2>
<div id="error-message" class="text-danger">Loading error details...</div>

<script>
  document.addEventListener("DOMContentLoaded", function() {
    fetch("/api/errorDetails") // Replace with the actual backend endpoint
      .then(response => {
        if (!response.ok) {
          throw new Error("Failed to fetch error details");
        }
        return response.json();
      })
      .then(data => {
        const errorMessage = document.getElementById("error-message");
        errorMessage.textContent = data.errorMessage || "An unexpected error occurred.";
      })
      .catch(error => {
        console.error("Error loading error details:", error);
        document.getElementById("error-message").textContent = "Failed to load error details. Please try again later.";
      });
  });
</script>

<%@ include file="footer.jsp" %>