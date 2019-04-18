<%@ page import="ru.spartars.domain.Auto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Catalog</title>
    <%@ include file="bootstrap.jsp" %>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <h1>Catalog</h1>
            <a href="<%= request.getContextPath() %>/catalog?action=create" class="btn btn-primary mt-2">Add new auto</a>
            <form class="mt-3" action="<%= request.getContextPath() %>/catalog/">
                <input name="q" class="form-control" type="search" placeholder="Search">
            </form>

            <div class="row">
                <% if (request.getAttribute("items") != null) { %>
                <% for (Auto item : (List<Auto>) request.getAttribute("items")) { %>
                <div class="col-sm-6 mt-3">
                    <div class="card">
                        <img src="<%= request.getContextPath() %>/images/<%= item.getImage() %>" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title"><%= item.getName() %>
                            </h5>
                            <p class="card-text"><%= item.getDescription()%>
                            </p>
                            <a href="<%= request.getContextPath() %>/catalog?action=details&id=<%= item.getId() %>"
                               class="btn btn-primary">Details</a>

                            <a href="<%= request.getContextPath() %>/catalog?action=update&id=<%=item.getId()%>" class="btn btn-primary">Update</a>
                            <a href="<%= request.getContextPath() %>/catalog?action=delete&id=<%=item.getId()%>" class="btn btn-primary">Delete</a>
                        </div>
                    </div>
                </div>
                <% } %>
                <% } %>
            </div>
        </div>
    </div>
</div>
</body>
</html>   