
<%--
  Created by IntelliJ IDEA.
  User: User113
  Date: 02.04.2024
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<table border=1>
    <thead>
    <tr>
        <th>Data</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="mealServlet"  class="ru.javawebinar.topjava.web.MealServlet"/>
    <c:set var="meals" value="${mealServlet.userMealList}" />
    <c:forEach items="${meals}" var="usermeal" >
        <tr>
            <td> ${usermeal.dateTime} </td>
            <td> ${usermeal.description} </td>
            <td> ${usermeal.calories} </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
