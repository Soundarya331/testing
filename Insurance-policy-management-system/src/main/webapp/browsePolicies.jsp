<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<h2>Browse Policies</h2>
<p>Below are the available policies. Click Purchase to buy a policy (customers only).</p>

<div id="policies-container" class="row">
  <!-- Policies will be dynamically loaded here -->
</div>

<div id="no-policies-alert" class="alert alert-info" style="display: none;">No policies available to browse.</div>

<script>
  document.addEventListener("DOMContentLoaded", function() {
    fetch("/api/policies") // Replace with the actual backend endpoint
      .then(response => {
        if (!response.ok) {
          throw new Error("Failed to fetch policies");
        }
        return response.json();
      })
      .then(policies => {
        const container = document.getElementById("policies-container");
        const noPoliciesAlert = document.getElementById("no-policies-alert");

        if (policies.length === 0) {
          noPoliciesAlert.style.display = "block";
          return;
        }

        policies.forEach(policy => {
          const card = document.createElement("div");
          card.className = "col-md-4 mb-3";
          card.innerHTML = `
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">${policy.name}</h5>
                <p class="card-text">Coverage: ${policy.coverageAmount}</p>
                <p class="card-text">Premium: ${policy.premiumAmount}</p>
                <p class="card-text">Duration: ${policy.durationYears} years</p>
                <a href="purchasePolicy?id=${policy.id}" class="btn btn-primary">Purchase</a>
              </div>
            </div>
          `;
          container.appendChild(card);
        });
      })
      .catch(error => {
        console.error("Error loading policies:", error);
      });
  });
</script>

<%@ include file="footer.jsp" %>