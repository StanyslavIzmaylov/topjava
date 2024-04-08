<%--
  Created by IntelliJ IDEA.
  User: User113
  Date: 05.04.2024
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add meal</title>
</head>
<body>
<form  action='addmeal' method="post">
    Data Time   : <input type="datetime-local"  name="dataTime"/><br/>
    Description : <input type="text"  name="description"/><br/>
    Calories    : <input type="text"  name="calories"/><br/>
    <button type="submit" class="btn btn-success">Save</button>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>