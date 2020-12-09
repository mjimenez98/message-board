<%@tag description="postTemplate" pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" rtexprvalue="true" %>
<%@attribute name="postTitle" required="true" rtexprvalue="true" %>
<%@attribute name="postUser" required="true" rtexprvalue="true" %>
<%@attribute name="post" required="true" rtexprvalue="true" %>
<%@attribute name="pid" required="true" rtexprvalue="true" %>

<html>
<head>
<meta charset="utf-8" content="">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" type=""></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js" type=""></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" type=""></script>
<title>${title}</title>
</head>
<body>
<div id="body">
    <p>${postTitle}</p>
    <p>${postUser}</p>
    <p>${post}</p>
</div>

<a href="DownloadXML.jsp?pid=<%=pid%>">
    <button type="button" class="btn btn-dark btn-sm">Download XML</button>
</a>
<a href="PostXML.jsp?pid=<%=pid%>">
    <button type="button" class="btn btn-dark btn-sm">XML to HTML</button>
</a>
<a href="RawXML.jsp?pid=<%=pid%>">
    <button type="button" class="btn btn-dark btn-sm">Raw XML</button>
</a>
</body>
</html>