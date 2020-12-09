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

<a href="../../DownloadXML.jsp?postId=<%=pid%>">
    <button type="button" class="btn btn-dark btn-sm">Download XML</button>
</a>
<a href="../../PostXML.jsp?postId=<%=pid%>">
    <button type="button" class="btn btn-dark btn-sm">XML to HTML</button>
</a>
<a href="../../RawXML.jsp?postId=<%=pid%>">
    <button type="button" class="btn btn-dark btn-sm">Raw XML</button>
</a>
<%--<button type="button" class="btn btn-primary" onclick="switchPage('DownloadXML.jsp')">Download XML</button>--%>
<%--<!--Not clear about what is asked so I created two implementations -->--%>
<%--<!--Uses XSLT to transform XML into HTML Table-->--%>
<%--<button type="button" class="btn btn-primary" onclick="switchPage('PostXML.jsp')">XML to HTML</button>--%>
<%--<!--Manually Converts Message into Viewable XML in HTML-->--%>
<%--<button type="button" class="btn btn-primary" onclick="switchPage('RawXML.jsp')">Raw XML</button>--%>
<%--<script type="text/javascript">--%>
<%--    function switchPage(pageURL)--%>
<%--    {--%>
<%--        window.location.href = pageURL;--%>
<%--    }--%>
<%--</script>--%>
</body>
</html>