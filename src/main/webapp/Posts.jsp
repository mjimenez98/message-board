<%@ page import="models.Post" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="models.Attachment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Message Board</title>
    </head>
    <body>
        <%
            // Allow access only if session exists
            if (session.getAttribute("user") == null) {
                response.sendRedirect("Login.jsp");
            }
            String user = (String) session.getAttribute("user");

            if (session.getAttribute("error") != null) {
        %>
                <div class="row">
                    <h1 style="background-color:tomato;"><%= session.getAttribute("error") %></h1>
                </div>
        <% } %>
        <div class="row">
            <h1>Posts</h1>
            <%
                LinkedList<Post> posts = (LinkedList<Post>) request.getAttribute("posts");
                if (request.getAttribute("posts") != null) {
                    for (Post post : posts) {
                        Attachment attachment = post.getAttachment();
                        boolean belongsToUser = (user != null && user.equals(post.getUsername()));
            %>
                        <p><%= post.getTitle() + " - " + post.getUsername() %></p>
                        <p><%= post.getMessage() %></p>
                        <% if (attachment != null) { %>
                            <div class="media">
                                <img src="..." class="mr-3" alt="...">
                                <div class="media-body">
                                    <h5 class="mt-0"><%= attachment.getName() %></h5>
                                    <p><%= attachment.getContentType() + " - " +  attachment.printSize() %></p>
                                    <% if (belongsToUser) { %>
                                        <form action="posts/attachments" method="post" enctype="multipart/form-data">
                                            <input type="hidden" name="postId" value="<%= post.getPostId() %>">
                                            <input type="hidden" name="attachmentId" value="<%= attachment.getAttachmentId() %>">

                                            <label for="file">Update file:</label>
                                            <input type="file" name="file" size="50"/>

                                            <input type="submit" name="request" class="btn button-color" value="Update">
                                        </form>
                                    <% } %>
                                </div>
                            </div>
                        <% } %>
                        <% if (belongsToUser) { %>
                                <form action="posts" method="post">
                                    <input type="hidden" name="postId" value="<%= post.getPostId() %>">
                                    <input type="submit" name="request" class="btn button-color" value="delete">
                                </form>
                        <% } %>
                        <hr/>
            <%
                    }
                }
            %>
        </div>
        <hr/>
        <div class="row">
            <div class="col-md-12">
                <form action="posts" method="post" enctype="multipart/form-data">
                    <h1> Create a Post </h1>
                    <fieldset>
                        <label for="title">Title</label>
                        <input type="text" id="title" name="title">

                        <input type="hidden" name="username" value="<%= user %>">

                        <label for="message">Message</label>
                        <input type="text" id="message" name="message">

                        <label for="file">Attach a file:</label>
                        <input type="file" id="file" name="file" size="50"/>
                    </fieldset>
                    <button type="submit" name="request" value="create">Create</button>
                </form>
                <form action="<%= response.encodeURL("LogoutServlet") %>" method="post">
                    <input type="submit" value="Logout">
                </form>
            </div>
        </div>
    </body>
</html>
