<%--
  Created by IntelliJ IDEA.
  User: avsat
  Date: 16.04.2019
  Time: 20:20
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
    <title>Document</title>
    <%@ include file="bootstrap.jsp" %>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <h1>Create auto</h1>
            <form class="mt-3" action="<%= request.getContextPath() %>/catalog?action=create" method="post"
                  enctype="multipart/form-data">

                <div class="form-group">
                    <label for="name">Auto Name</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Auto name" required>
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea name="description" class="form-control" id="description" placeholder="Auto description"
                              required></textarea>
                </div>
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="file" name="file" accept="image/*" required>
                    <label class="custom-file-label" for="file">Choose file...</label>
                </div>

                <button type="submit" class="btn btn-primary mt-2">Create</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>