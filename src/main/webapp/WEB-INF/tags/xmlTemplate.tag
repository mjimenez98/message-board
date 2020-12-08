<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "x" uri = "http://java.sun.com/jsp/jstl/xml" %>
<%@attribute name="title" required="true" rtexprvalue="true" %>
<%@attribute name="postTitle" required="true" rtexprvalue="true" %>
<%@attribute name="postUser" required="true" rtexprvalue="true" %>
<%@attribute name="message" required="true" rtexprvalue="true" %>

<html>
<head>
    <title>${title}</title>
</head>

<body>
<h3>Post</h3>
<c:set var = "xmltext">
    <posts>
        <post>
            <postTitle>${postTitle}</postTitle>
            <postUser>${postUser}</postUser>
            <message>${message}</message>
        </post>
    </posts>
</c:set>

<c:import url = "/style.xsl" var = "xslt"/>
<x:transform xml = "${xmltext}" xslt = "${xslt}"/>

</body>
</html>