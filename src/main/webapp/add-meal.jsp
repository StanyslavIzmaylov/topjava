<%--
  Created by IntelliJ IDEA.
  User: User113
  Date: 05.04.2024
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add meal</title>
</head>
<body>
<form>

    <form method="POST" action='meals' name="addMeal">
        Data Time   : <input type="date" readonly="readonly" name="dataTime"/>
        Description : <input type="text" readonly="readonly" name="description"/>
        Calories    : <input type="text" readonly="readonly" name="calories"/>
        <input type="submit" value="Сохранить">
    </form>
</form>
</body>
</html>
