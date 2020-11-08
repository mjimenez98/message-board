import db.DBPost;
import models.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "PostServlet")
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all params from request
        String title = request.getParameter("title");
        String username = request.getParameter("username");
        String message = request.getParameter("message");

        DBPost.addPost(title, username, message);

        response.sendRedirect("/message_board_war/posts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletConfig config = getServletConfig();
        int limit = Integer.parseInt(config.getInitParameter("limit"));

        LinkedList<Post> posts = DBPost.getPosts(limit);
        request.setAttribute("posts", posts);

        RequestDispatcher rd = request.getRequestDispatcher("Posts.jsp");
        rd.forward(request, response);
    }
}
