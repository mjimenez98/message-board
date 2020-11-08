<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
  Created by IntelliJ IDEA.
  User: dina
  Date: 2020-11-05
  Time: 8:43 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href='https://fonts.googleapis.com/css?family=Nunito:400,300' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
<%
    //allow access only if session exists
    String user = null;
    if (session.getAttribute("user") != null) {
        response.sendRedirect("Posts.jsp");
    }
%>
<div class="row">
    <div class="col-md-12">
        <form action="LoginPageServlet" method="post">
            <h1> Login </h1>
            <fieldset>
                <legend><span class="number">👨🏻‍🎓</span> Your Info</legend>
                <label for="username">Username:</label>
                <input type="text" id="username" name="username">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password">
                <c:if test="${not empty loginError}">
                    <p>Error: <c:out value = "${loginError}"/></p>
                </c:if>
            </fieldset>
            <button type="submit">Login</button>
        </form>
    </div>
</div>
</body>
</html>
