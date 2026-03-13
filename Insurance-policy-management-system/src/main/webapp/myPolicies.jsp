<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<h2>My Policies</h2>
<p>Policies you've purchased will appear here.</p>
<%
    java.util.List purchases = (java.util.List) request.getAttribute("purchases");
    if (purchases != null && !purchases.isEmpty()) {
%>
  <table class="table table-striped">
    <thead>
      <tr>
        <th>Policy Name</th>
        <th>Purchase Date</th>
        <th>Premium Paid</th>
      </tr>
    </thead>
    <tbody>
<%
      for (Object obj : purchases) {
          try {
              java.lang.reflect.Method getPolicyName = obj.getClass().getMethod("getPolicyName");
              java.lang.reflect.Method getPurchaseDate = obj.getClass().getMethod("getPurchaseDate");
              java.lang.reflect.Method getPremiumPaid = obj.getClass().getMethod("getPremiumPaid");
              Object name = getPolicyName.invoke(obj);
              Object date = getPurchaseDate.invoke(obj);
              Object paid = getPremiumPaid.invoke(obj);
%>
      <tr>
        <td><%= name %></td>
        <td><%= date %></td>
        <td><%= paid %></td>
      </tr>
<%
          } catch (Exception e) {
              // skip
          }
      }
%>
    </tbody>
  </table>
<%
    } else {
%>
  <div class="alert alert-info">You have not purchased any policies yet.</div>
<%
    }
%>
<%@ include file="footer.jsp" %>