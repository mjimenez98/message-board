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
                        <form action="posts" method="post">
                            <% if (session.getAttribute("editMessage") != null &&
                                    session.getAttribute("editMessage") == (Integer) post.getPostId()) {
                            %>
                                 <textarea type="text" name="editTitle"><%= post.getTitle() %></textarea>

                                 <%= " - " + post.getUsername() %>

                                 <textarea type="text" name="editMessage"><%= post.getMessage() %></textarea>

                                 <button type="submit" name="request" value="save">Save Post</button>
                            <% }
                               else {
                            %>
                                <p><%= post.getTitle() + " - " + post.getUsername() %></p>
                                <p><%= post.getMessage() %></p>
                                <% if (post.getCreatedAt().isBefore(post.getUpdatedAt())) { %>
                                     <p><i>Edited <%= post.getUpdatedAt() %></i></p>
                            <%     }
                               }
                            %>
                            <% if (belongsToUser) { %>
                                <input type="hidden" name="postId" value="<%= post.getPostId() %>">
                                <button type="submit" name="request" value="edit">Edit Post</button>
                                <button type="submit" name="request" value="delete">Delete Post</button>
                            <% } %>
                        </form>
                        <% if (attachment != null) { %>
                            <div class="media">
                                <img src="${pageContext.request.contextPath}/images/attachment.png" class="mr-3"
                                     alt="attachment" width="16" height="16">
                                <div class="media-body">
                                    <h5 class="mt-0"><%= attachment.getName() %></h5>
                                    <p>
                                        <%= attachment.getContentType() + " - " +  attachment.printSize()%>
                                        <% if (post.isUpdated()) { %>
                                            <small class="text-muted">
                                                <%= " - Last updated on " + post.getUpdatedAt() %>
                                            </small>
                                        <% } %>
                                    </p>
                                    <% if (belongsToUser) { %>
                                        <form action="posts/attachments" method="post" enctype="multipart/form-data">
                                            <input type="hidden" name="postId" value="<%= post.getPostId() %>">

                                            <label for="file">Update file:</label>
                                            <input type="file" name="file" size="50"/>

                                            <button type="submit" name="request" value="update">Update Attachment</button>
                                            <button type="submit" name="request" value="delete">Delete Attachment</button>
                                        </form>
                                    <% } %>
                                </div>
                            </div>
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
                <form action="posts" method="get">
                    <h1> Search for Posts </h1>
                    <fieldset>
                        <label for="user">User</label>
                        <input type="text" id="user" name="user">

                        <label for="fromDate">From</label>
                        <input type="datetime-local" id="fromDate" name="fromDate">

                        <label for="toDate">To</label>
                        <input type="datetime-local" id="toDate" name="toDate">

                        <label for="hashtags">Hashtags</label>
                        <input type="text" id="hashtags" name="hashtags">

                        <button type="submit" name="request" value="search">Search</button>
                    </fieldset>
                </form>
            </div>
        </div>
    </body>
</html>
