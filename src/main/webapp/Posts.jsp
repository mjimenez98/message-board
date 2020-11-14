<%@ page import="models.Post" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="models.Attachment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta charset="utf-8" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/bootstrap.min.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" type=""></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js" type=""></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" type=""></script>

        <title>Message Board</title>
    </head>
    <body>
        <div class="container mw-100">
            <%-- Title --%>
            <div class="row py-5 bg-dark">
                <div class="col-12">
                    <h1 class="text-center text-white">Message Board 387</h1>
                </div>
            </div>

            <%-- Alerts --%>
            <div class="row justify-content-center">
                <div class="col-md-10 mt-2">
                    <%
                        // Allow access only if session exists
                        if (session.getAttribute("user") == null) {
                            response.sendRedirect("Login.jsp");
                        }
                        String user = (String) session.getAttribute("user");

                        if (session.getAttribute("error") != null) {
                    %>
                        <div class="alert alert-danger text-center" role="alert">
                            <%= session.getAttribute("error") %>
                        </div>
                    <%
                        }
                    %>
                </div>
            </div>

            <%-- Posts --%>
            <div>
                <%-- Title --%>
                <div class="row">
                    <div class="col-12">
                        <h1>Posts</h1>
                    </div>
                </div>

                <%-- Post --%>
                <%
                    LinkedList<Post> posts = (LinkedList<Post>) request.getAttribute("posts");
                    if (request.getAttribute("posts") != null) {
                        for (Post post : posts) {
                            Attachment attachment = post.getAttachment();
                            boolean belongsToUser = (user != null && user.equals(post.getUsername()));
                %>
                            <div class="row">
                                <div class="col-12">
                                    <form action="posts" method="post">
                                        <% if (session.getAttribute("editMessage") != null &&
                                                session.getAttribute("editMessage") == (Integer) post.getPostId()) {
                                        %>
                                            <div class="form-group">
                                                <label for="editTitle"></label>
                                                <textarea id="editTitle" name="editTitle" class="form-control"  rows="5" cols="1">
                                                    <%= post.getTitle() %>
                                                </textarea>
                                            </div>

                                            <div class="form-group">
                                                <label for="editMessage"></label>
                                                <textarea id="editMessage" name="editMessage" class="form-control" rows="5" cols="1">
                                                    <%= post.getMessage() %>
                                                </textarea>
                                            </div>

                                            <button type="submit" name="request" value="save">Save Post</button>
                                        <% }
                                            else {
                                        %>
                                                <p><%= post.getTitle() + " - " + post.getUsername() %></p>
                                                <p><%= post.getMessage() %></p>
                                                <%
                                                    if (post.getCreatedAt().isBefore(post.getUpdatedAt())) {
                                                %>
                                                    <p class="font-italic">Edited <%= post.getUpdatedAt() %></p>
                                        <%
                                                    }
                                            }
                                        %>
                                        <%
                                            if (belongsToUser) {
                                        %>
                                                <input type="hidden" name="postId" value="<%= post.getPostId() %>">
                                                <button type="submit" name="request" value="edit">Edit Post</button>
                                                <button type="submit" name="request" value="delete">Delete Post</button>
                                        <% } %>
                                    </form>


                                    <%
                                        if (attachment != null) {
                                    %>
                                        <div class="media">
                                            <img src="${pageContext.request.contextPath}/images/attachment.png" class="mr-3"
                                                 alt="attachment" width="16" height="16">
                                            <div class="media-body">
                                                <h5 class="mt-0"><%= attachment.getName() %></h5>
                                                <p>
                                                    <%= attachment.getContentType() + " - " +  attachment.printSize()%>

                                                    <%
                                                        if (post.isUpdated()) {
                                                    %>
                                                        <small class="text-muted">
                                                            <%= " - Last updated on " + post.getUpdatedAt() %>
                                                        </small>
                                                    <% } %>
                                                </p>
                                                <% if (belongsToUser) { %>
                                                    <form action="posts/attachments" method="post" enctype="multipart/form-data">
                                                        <input type="hidden" name="postId" value="<%= post.getPostId() %>">

<%--                                                        <label for="file">Update file:</label>--%>
                                                        <p>Update file:</p>
                                                        <input type="file" name="file" size="50"/>

                                                        <button type="submit" name="request" value="update">Update Attachment</button>
                                                        <button type="submit" name="request" value="delete">Delete Attachment</button>
                                                    </form>
                                                <% } %>
                                            </div>
                                        </div>
                                    <% } %>
                                </div>
                            </div>
                <%
                        }
                    }
                %>
            </div>

            <hr/>

            <%-- Create a post --%>
            <div class="mb-3">
                <%-- Header --%>
                <div class="container">
                    <div class="row">
                        <%-- Title --%>
                        <div class="col-10">
                            <h1>Create a Post</h1>
                        </div>

                        <%-- Logout --%>
                        <div class="col-2 text-right">
                            <form action="<%= response.encodeURL("LogoutServlet") %>" method="post" class="mt-2">
                                <button type="submit" name="request" value="Logout" class="btn btn-dark">
                                    Logout
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <%-- Form --%>
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <form action="posts" method="post" enctype="multipart/form-data">
                                <div class="form-group">
                                    <label for="title">Title</label>
                                    <textarea id="title" name="title" class="form-control" cols="5" rows="1"></textarea>
                                </div>

                                <div class="form-group">
                                    <label for="message">Message</label>
                                    <textarea id="message" name="message" class="form-control" cols="5" rows="3"></textarea>
                                </div>

                                <div class="form-group">
                                    <label for="file">Attach a file:</label>
                                    <input type="file" id="file" name="file" size="50"/>
                                </div>

                                <div class="form-group">
                                    <input type="hidden" name="username" value="<%= user %>">
                                </div>

                                <div class="text-center">
                                    <button type="submit" name="request" value="create" class="btn btn-dark">Create</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
