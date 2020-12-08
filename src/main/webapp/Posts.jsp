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
        <%-- Authentication --%>
        <%
            // Allow access only if session exists
            if (session.getAttribute("user") == null) {
                response.sendRedirect("Login.jsp");
            }

            String user = (String) session.getAttribute("user");
        %>

        <div class="container mw-100">
            <%-- Title --%>
            <div class="row py-5 bg-dark">
                <div class="col-12">
                    <h1 class="text-center text-white">Message Board 387</h1>
                </div>
            </div>

            <%-- Alerts --%>
            <div class="row justify-content-center">
                <div class="col-md-12 mt-2">
                    <%
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

            <%-- Logout --%>
            <div class="row justify-content-end">
                <div class="col-3">
                    <form action="<%= response.encodeURL("LogoutServlet") %>" method="post" class="mt-2">
                        <button type="submit" name="request" value="Logout" class="btn btn-dark">
                            Logout
                        </button>
                    </form>
                </div>
            </div>

            <%-- Search --%>
            <div class="row mb-3">
                <div class="container">
                    <div class="row">
                        <%-- Search form --%>
                        <div class="col-12">
                            <h1 class="mb-1">Search</h1>
                            <form action="posts" method="get" class="form-inline">
                                <label for="user" class="mr-1">User</label>
                                <input type="text" id="user" name="user" class="mr-2">

                                <label for="fromDate" class="mr-1">From</label>
                                <input type="datetime-local" id="fromDate" name="fromDate" class="mr-2">

                                <label for="toDate" class="mr-1">To</label>
                                <input type="datetime-local" id="toDate" name="toDate" class="mr-2">

                                <label for="hashtags" class="mr-1">Hashtags</label>
                                <input type="text" id="hashtags" name="hashtags" class="mr-2">

                                <button type="submit" name="request" value="search" class="btn btn-dark mt-2">Search</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <hr/>

            <%-- Posts --%>
            <div>
                <%-- Title --%>
                <div class="row mb-2">
                    <div class="container">
                        <div class="row">
                            <div class="col-12">
                                <h1>Posts</h1>
                            </div>
                        </div>
                    </div>
                </div>

                <%-- Post --%>
                <%
                    LinkedList<Post> posts = (LinkedList<Post>) request.getAttribute("posts");
                    if (request.getAttribute("posts") != null) {
                        for (Post post : posts) {
                            Attachment attachment = post.getAttachment();
                            boolean belongsToUser = (user != null && user.equals(post.getUsername()));
                            int pid = post.getPostId();
                %>
                            <div class="row mt-1 mb-3">
                                <div class="container">
                                    <div class="row mb-3">
                                        <div class="col-12 bg-light py-3">
                                            <%
                                                // Display edit options
                                                if (session.getAttribute("editMessage") != null &&
                                                    session.getAttribute("editMessage") == (Integer) post.getPostId()) {
                                            %>
                                                    <form action="posts" method="post" class="mb-3">
                                                        <div class="form-group">
                                                            <label for="editTitle">Title</label>
                                                            <textarea id="editTitle" name="editTitle" class="form-control" rows="3" cols="1"><%= post.getTitle() %></textarea>
                                                        </div>

                                                        <div class="form-group">
                                                            <label for="editMessage">Message</label>
                                                            <textarea id="editMessage" name="editMessage" class="form-control" rows="3" cols="1"><%= post.getMessage() %></textarea>
                                                        </div>

                                                        <input type="hidden" name="postId" value="<%= post.getPostId() %>">
                                                        <button type="submit" name="request" value="save" class="btn btn-dark btn-sm">
                                                            Save
                                                        </button>
                                                    </form>
                                            <%
                                                }
                                                // Display regular post
                                                else {
                                            %>
                                                    <div>
                                                        <h3><%= post.getTitle() %></h3>

                                                        <h5 class="text-muted mb-1"><%= post.getUsername() %></h5>

                                                        <p><%= post.getMessage() %></p>

                                                        <% if (attachment != null) { %>
                                                            <hr/>
                                                            <div class="media">
                                                                <div class="media-body">
                                                                    <h6 class="mt-0"><%= attachment.getName() %></h6>

                                                                    <p><%= attachment.getContentType() + " - " +  attachment.printSize()%></p>

                                                                    <% if (belongsToUser) { %>
                                                                        <form action="posts/attachments" method="post"
                                                                              enctype="multipart/form-data" class="mb-3">
                                                                            <input type="hidden" name="postId" value="<%= post.getPostId() %>">

                                                                            <label for="file">Update file:</label>
                                                                            <input type="file" name="file" size="50"/>

                                                                            <br/>
                                                                            <div class="mt-1">
                                                                                <button type="submit" name="request" value="download"
                                                                                        class="btn btn-dark btn-sm">
                                                                                    Download
                                                                                </button>
                                                                            </div>
                                                                            <div class="mt-1">
                                                                                <button type="submit" name="request" value="update"
                                                                                        class="btn btn-dark btn-sm">
                                                                                    Update Attachment
                                                                                </button>
                                                                                <button type="submit" name="request" value="delete"
                                                                                        class="btn btn-dark btn-sm">
                                                                                    Delete Attachment
                                                                                </button>
                                                                            </div>
                                                                        </form>
                                                                    <% } %>
                                                                </div>

                                                                <img src="${pageContext.request.contextPath}/images/attachment.png"
                                                                     class="ml-3" alt="attachment" width="40" height="40">
                                                            </div>
                                                        <% } %>

                                                        <% if (post.getCreatedAt().isBefore(post.getUpdatedAt())) { %>
                                                            <p class="font-italic">Edited <%= post.getUpdatedAt() %></p>
                                                        <% } %>
                                                    </div>
                                            <% } %>

                                            <%-- Edit and delete buttons --%>
                                            <%
                                                if (belongsToUser) {
                                            %>

                                                    <form action="posts" method="post">
                                                        <input type="hidden" name="postId" value="<%= post.getPostId() %>">

                                                        <button type="submit" name="request" value="edit"
                                                                class="btn btn-dark btn-sm mr-2">
                                                            Edit
                                                        </button>

                                                        <button type="submit" name="request" value="delete"
                                                                class="btn btn-dark btn-sm">
                                                            Delete
                                                        </button>
                                                    </form>
                                            <% } %>
                                                <a href="PostDownload.jsp">
                                                <button type="button" class="btn btn-dark btn-sm" OnClick=<%session.setAttribute("pid", pid);%>>View</button>
                                                </a>
                                        </div>
                                    </div>
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
                        <div class="col-12">
                            <h1>Create a Post</h1>
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
