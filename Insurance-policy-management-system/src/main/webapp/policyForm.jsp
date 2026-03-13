<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    String title = request.getParameter("policy.policyId") != null && !request.getParameter("policy.policyId").isEmpty() ? "Edit Policy" : "Create Policy";
%>
<h2><%= title %></h2>
<form action="savePolicy" method="post">
  <input type="hidden" name="policy.policyId" value="${policy.policyId}" />
  <div class="mb-3">
    <label class="form-label">Policy Name</label>
    <input type="text" name="policy.policyName" class="form-control" value="${policy.policyName}" required />
  </div>
  <div class="mb-3">
    <label class="form-label">Coverage Amount</label>
    <input type="number" step="0.01" name="policy.coverageAmount" class="form-control" value="${policy.coverageAmount}" required />
  </div>
  <div class="mb-3">
    <label class="form-label">Premium Amount</label>
    <input type="number" step="0.01" name="policy.premiumAmount" class="form-control" value="${policy.premiumAmount}" required />
  </div>
  <div class="mb-3">
    <label class="form-label">Duration (years)</label>
    <input type="number" name="policy.durationYears" class="form-control" value="${policy.durationYears}" required />
  </div>
  <div class="mb-3">
    <button type="submit" class="btn btn-primary">Save Policy</button>
    <a class="btn btn-secondary" href="policies.jsp">Cancel</a>
  </div>
</form>
<%@ include file="footer.jsp" %>