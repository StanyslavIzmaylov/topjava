<%--
  Created by IntelliJ IDEA.
  User: User113
  Date: 05.04.2024
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add meal</title>
</head>
<body>
<form>

    <form method="POST" action='meals.jsp' name="frmAddUser">
        User ID : <input type="date" readonly="readonly" name="mealToId"
                         value="<jsp:useBean id="usermeal" scope="request" type="ru.javawebinar.topjava.model.MealTo"/>
                         <c:out value="${usermeal.dateTime}" />" /> <br />
        First Name : <input
            type="text" name="firstName"
            value="<c:out value="${user.firstName}" />" /> <br />
        Last Name : <input
            type="text" name="lastName"
            value="<c:out value="${user.lastName}" />" /> <br />
        DOB : <input
            type="text" name="dob"
            value="<fmt:formatDate pattern="MM/dd/yyyy" value="${user.dob}" />" /> <br />
        Email : <input type="text" name="email"
                       value="<c:out value="${user.email}" />" /> <br /> <input
            type="submit" value="Submit" />
    </form>
</form>
</body>
</html>
