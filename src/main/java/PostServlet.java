import db.DBPost;
import models.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

@WebServlet(name = "PostServlet")
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("error", null);

        if (request.getParameter("request") != null) {
            if (request.getParameter("request").equals("create")) {
                // Get all params from request
                String title = request.getParameter("title");
                String username = request.getParameter("username");
                String message = request.getParameter("message");

                Post createdPost = DBPost.createPost(title, username, message);
                if (createdPost == null)
                    session.setAttribute("error", "Could not create post");
            } else if (request.getParameter("request").equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));

                Post deletedPost = DBPost.deletePost(id);
                if (deletedPost == null)
                    session.setAttribute("error", "Could not delete post");
            }
        }

        response.sendRedirect("/message_board_war/posts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get params
        ServletConfig config = getServletConfig();
        int limit = Integer.parseInt(config.getInitParameter("limit"));

        LinkedList<Post> posts = DBPost.getPosts(limit);
        request.setAttribute("posts", posts);

        if (request.getParameter("request") != null) {
            if (request.getParameter("request").equals("search")) {
                String username = request.getParameter("user");
                String fromDate = request.getParameter("fromDate");
                String toDate = request.getParameter("toDate");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:'00'");
                fromDate = ((fromDate != "") ? LocalDateTime.parse(fromDate).format(formatter) : null);
                toDate = ((toDate != "") ? LocalDateTime.parse(toDate).format(formatter) : null);
                String[] hashtags = request.getParameter("hashtags").split(",");
                if (hashtags[0] != "") {
                    for (int i = 0; i < hashtags.length; i++) {
                        hashtags[i] = hashtags[i].replaceAll("\\s+", "");
                        hashtags[i] = "%#" + hashtags[i] + "%";
                    }
                }
                LinkedList<Post> searchResults = DBPost.getPosts(username, fromDate, toDate, hashtags);
                request.setAttribute("searchResults", searchResults);
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher("Posts.jsp");
        rd.forward(request, response);
    }
}
