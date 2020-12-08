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
    <p>&lt;xml version="1.0" encoding="UTF-8"?&gt;</p>
    <p>&lt;posts&gt;</p>
    <div class="tab">
    <p>&lt;title&gt;${postTitle}&lt;/title&gt;</p>
    <p>&lt;username&gt;${postUser}&lt;/username&gt;</p>
    <p>&lt;message&gt;${post}&lt;/message&gt</p>
    </div>
    <p>&lt;/posts&gt;</p>
</div>
</body>
<style type="text/css">
    .tab { margin-left: 40px; }
</style>
</html>