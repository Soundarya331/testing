<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<div class="d-flex justify-content-between align-items-center mb-3">
  <h2>All Policies</h2>
  <a class="btn btn-success" href="policyForm.jsp">Create Policy</a>
</div>
<div id="policies-container">
  <div class="alert alert-info">Loading policies...</div>
</div>

<script>
  document.addEventListener("DOMContentLoaded", function() {
    fetch("/api/policies") // Replace with the actual backend endpoint
      .then(response => {
        if (!response.ok) {
          throw new Error("Failed to fetch policies");
        }
        return response.json();
      })
      .then(data => {
        const container = document.getElementById("policies-container");
        if (data.policies && data.policies.length > 0) {
          let table = `<table class='table table-striped'>
                        <thead>
                          <tr>
                            <th>Policy Name</th>
                            <th>Coverage</th>
                            <th>Premium</th>
                            <th>Duration (yrs)</th>
                            <th>Actions</th>
                          </tr>
                        </thead>
                        <tbody>`;
          data.policies.forEach(policy => {
            table += `<tr>
                        <td>${policy.policyName}</td>
                        <td>${policy.coverageAmount}</td>
                        <td>${policy.premiumAmount}</td>
                        <td>${policy.durationYears}</td>
                        <td>
                          <a class='btn btn-primary btn-sm' href='editPolicy?id=${policy.policyId}'>Edit</a>
                          ${!policy.isDeleted ? `<a class='btn btn-danger btn-sm' href='deletePolicy?id=${policy.policyId}'>Delete</a>` : ''}
                        </td>
                      </tr>`;
          });
          table += `</tbody></table>`;
          container.innerHTML = table;
        } else {
          container.innerHTML = `<div class='alert alert-info'>No policies available.</div>`;
        }
      })
      .catch(error => {
        console.error("Error loading policies:", error);
        document.getElementById("policies-container").innerHTML = `<div class='alert alert-danger'>Failed to load policies. Please try again later.</div>`;
      });
  });
</script>

<%@ include file="footer.jsp" %>