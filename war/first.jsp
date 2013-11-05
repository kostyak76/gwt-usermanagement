<%--
  Created by IntelliJ IDEA.
  User: kostyak
  Date: 11/2/13
  Time: 2:21 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%! int count = 0;%>
<% int simpleCount = 1; %>

<!doctype html>
<html>
<head>
    <title></title>
</head>
<body>
<div>You are the <%= ++count%> visitor of our site</div>
<div>And here teh local variable: <%= simpleCount%></div>

<%--This is our footer--%>
<jsp:include page="footer.jsp"/>

</body>
</html>