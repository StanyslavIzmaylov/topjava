
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update meal Meal </title>
</head>
<body>
<form action='meals?action=editmeal' method="post">
    <input type="hidden" value=
            "${meal.id}" readonly="readonly" name="id"/><br/>
    <label> Data Time   :</label> <input type="datetime-local" value=
        "${meal.dateTime}" name="dataTime"/><br/>
    <label> Description :</label> <input type=" text" value=
        "${meal.description}" name="description"/><br/>
    <label> Calories    :</label> <input type="text" value=
        '${meal.calories}' name="calories"/><br/>
    <button type="submit" class="btn btn-success">Save</button>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>