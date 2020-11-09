<%@ page import="models.Post" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Message Board</title>
    </head>
    <body>
        <%
            // Allow access only if session exists
            String user = null;
            if (session.getAttribute("user") == null) {
                response.sendRedirect("Login.jsp");
            } else user = (String) session.getAttribute("user");
        %>
        <div class="row">
            <h1>Posts</h1>
            <%
                LinkedList<Post> posts = (LinkedList<Post>) request.getAttribute("posts");
                if (request.getAttribute("posts") != null) {
                    for(Post post : posts) {
            %>
                        <p><%= post.getTitle() + " - " + post.getUsername() %></p>
                        <p><%= post.getMessage()%></p>
                        <%
                            if (user != null && user.equals(post.getUsername())) { %>
                            <form action="posts" method="post">
                                <input type="hidden" name="id" value="<%= post.getId() %>">
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
                <form action="posts" method="post">
                    <h1> Create a Post </h1>
                    <fieldset>
                        <label for="title">Title</label>
                        <input type="text" id="title" name="title">

                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" value=<%= user %>>

                        <label for="message">Message</label>
                        <input type="text" id="message" name="message">
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
