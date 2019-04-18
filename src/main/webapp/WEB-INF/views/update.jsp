<%@ page import="ru.spartars.domain.Auto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <%@ include file="bootstrap.jsp" %>
    <title>Update</title>
</head>
<body>
<% if (request.getAttribute("item") != null) { %>
<% Auto item = (Auto) request.getAttribute("item"); %>
<div class="container">
    <div class="row">
        <div class="col">
            <h1>Update</h1>
            <form class="mt-3" method="post"
                  action="<%= request.getContextPath() %>/catalog?action=update&id=<%=item.getId()%>"
                  enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name">Auto Name</label>
                    <input type="text" class="form-control" id="name" name="name"
                           placeholder="<%= item.getName()%>" value="<%= item.getName()%>">
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea name="description" class="form-control" id="description"
                    ><%= item.getDescription()%></textarea>
                </div>

                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="file" name="file" accept="image/*">
                    <label class="custom-file-label" for="file">Choose file...</label>
                </div>

                <% } %>
                <button name="update" class="btn btn-primary mt-2">Update</button>
                <a href="<%= request.getContextPath() %>/catalog/" class="btn btn-primary mt-2">Cancel</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>