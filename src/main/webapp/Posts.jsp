<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Message Board</title>
    </head>
    <body>
        <div class="row">
            <div class="col-md-12">
                <form action="posts" method="post">
                    <h1> Create a Post </h1>
                    <fieldset>
                        <label for="title">Title</label>
                        <input type="text" id="title" name="title">

                        <label for="username">Username</label>
                        <input type="text" id="username" name="username">

                        <label for="message">Message</label>
                        <input type="text" id="message" name="message">
                    </fieldset>
                    <button type="submit">Sign Up</button>
                </form>
            </div>
        </div>
    </body>
</html>
