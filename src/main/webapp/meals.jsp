
<%--
  Created by IntelliJ IDEA.
  User: User113
  Date: 02.04.2024
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <c:set var="meals" value="${mealServlet.mealToList}" />
    <c:forEach items="${meals}" var="usermeal" >
        <tr style="background-color:${usermeal.excess ? 'red' : 'greenyellow'}">
            <td>  <fmt:parseDate value="${usermeal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                  <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" /></td>
            <td> ${usermeal.description} </td>
            <td> ${usermeal.calories} </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
