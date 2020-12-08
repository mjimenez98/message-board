<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    String postTitle = session.getAttribute("postTitle").toString();
    String postUser = session.getAttribute("postUser").toString();
    String message = session.getAttribute("message").toString();
%>

<t:xmlTemplate title="View Post Page">
    <jsp:attribute name="postTitle"><%=postTitle%></jsp:attribute>
    <jsp:attribute name="postUser"><%=postUser%></jsp:attribute>
    <jsp:attribute name="post"><%=message%></jsp:attribute>
</t:xmlTemplate>
