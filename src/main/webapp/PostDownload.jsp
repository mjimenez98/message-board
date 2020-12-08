<%@ page import="db.DBPost" %>
<%@ page import="models.Post" %>
<%@ page import="models.Attachment" %>
<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%
    int pid = (int) session.getAttribute("pid");
    Post post = DBPost.getPost(pid);
    String title = post.getTitle();
    String user = post.getUsername();
    String message = post.getMessage();
    Attachment attach = post.getAttachment();
    String aname = "";
    if (attach != null) {
        aname = attach.getName();
    }
%>

<t:postTemplate title="View Post Page">
    <jsp:attribute name="postTitle"><%=title%></jsp:attribute>
    <jsp:attribute name="postUser"><%=user%></jsp:attribute>
    <jsp:attribute name="post"><%=message%></jsp:attribute>
    <jsp:attribute name="aname"><%=aname%></jsp:attribute>
</t:postTemplate>
