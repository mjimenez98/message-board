<%@ page import="models.Post" %>
<%@ page import="db.DBPost" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "x" uri = "http://java.sun.com/jsp/jstl/xml" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    int pid = Integer.parseInt(request.getParameter("pid"));
    Post post = DBPost.getPost(pid);
    String postTitle = post.getTitle();
    String user = post.getUsername();
    String message = post.getMessage();
%>

<t:xmlTemplate title="XML Page">
    <jsp:attribute name="postTitle"><%=postTitle%></jsp:attribute>
    <jsp:attribute name="postUser"><%=user%></jsp:attribute>
    <jsp:attribute name="message"><%=message%></jsp:attribute>
</t:xmlTemplate>