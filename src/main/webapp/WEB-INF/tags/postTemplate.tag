<%@tag description="postTemplate" pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" rtexprvalue="true" %>
<%@attribute name="postTitle" required="true" rtexprvalue="true" %>
<%@attribute name="postUser" required="true" rtexprvalue="true" %>
<%@attribute name="post" required="true" rtexprvalue="true" %>
<%@attribute name="aname" required="true" rtexprvalue="true" %>

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

<input type="button" value="Download as XML" name="DownloadXML"
       onclick=""/>
<button type="button" class="btn btn-primary" onclick="switchPage('PostXML.jsp')">View As XML</button>

<script type="text/javascript">
    function switchPage(pageURL)
    {
        window.location.href = pageURL;
    }
    function download(xmlFile)
    {
    }
</script>
</body>
</html>