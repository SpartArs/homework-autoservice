<%@ page import="ru.spartars.domain.Auto" %><%--
  Created by IntelliJ IDEA.
  User: avsat
  Date: 16.04.2019
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Delete</title>
</head>
<body>
<% if (request.getAttribute("item") != null) { %>
<% Auto item = (Auto) request.getAttribute("item"); %>
    <form action=""<%= request.getContextPath() %>/catalog/<%= item.getId() %>"" method="post">
        <button name="delete">Delete</button>
    </form>
<%}%>
</body>
</html>
