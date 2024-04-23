<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form action="users">
    <p><select size="3" name="user">
        <option disabled>Выберите пользователя</option>
        <option value="1" selected>User</option>
        <option value="2">Admin</option>
    </select></p>
    <p><input type="submit" value="Отправить"></p>
</form>
</body>
</html>