<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<h2>Purchase Details</h2>
<%
    Object purchase = request.getAttribute("purchase");
    if (purchase != null) {
        try {
            java.lang.reflect.Method getPolicyName = purchase.getClass().getMethod("getPolicyName");
            java.lang.reflect.Method getPurchaseDate = purchase.getClass().getMethod("getPurchaseDate");
            java.lang.reflect.Method getPremiumPaid = purchase.getClass().getMethod("getPremiumPaid");
            Object name = getPolicyName.invoke(purchase);
            Object date = getPurchaseDate.invoke(purchase);
            Object paid = getPremiumPaid.invoke(purchase);
%>
  <ul class="list-group">
    <li class="list-group-item"><strong>Policy:</strong> <%= name %></li>
    <li class="list-group-item"><strong>Purchase Date:</strong> <%= date %></li>
    <li class="list-group-item"><strong>Premium Paid:</strong> <%= paid %></li>
  </ul>
<%
        } catch (Exception e) {
            // ignore
        }
    } else {
%>
  <div class="alert alert-warning">Purchase details not found.</div>
<%
    }
%>
<%@ include file="footer.jsp" %>