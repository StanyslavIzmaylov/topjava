
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add meal</title>
</head>
<body>
<form  action='meals?action=addmeal' method="post">
    <input type="hidden" value=
            "${meal.id}" readonly="readonly" name="id"/><br/>
    Data Time   : <input type="datetime-local"  name="dataTime"/><br/>
    Description : <input type="text"  name="description"/><br/>
    Calories    : <input type="text"  name="calories"/><br/>
    <button type="submit" class="btn btn-success">Save</button>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>