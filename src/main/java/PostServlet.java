import db.DBAttachment;
import db.DBPost;
import models.Attachment;
import models.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

@WebServlet(name = "PostServlet")
@MultipartConfig(maxFileSize = 16177215) // 16 MB
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
                Part filePart = request.getPart("file");

                // Create post
                Post createdPost = DBPost.createPost(title, username, message);

                if (createdPost == null)
                    session.setAttribute("error", "Could not create post");
                else {
                    if (filePart != null) {
                        String name = filePart.getName();
                        int size = (int) filePart.getSize();
                        String type = filePart.getContentType();
                        InputStream file = filePart.getInputStream();
                        int postId = createdPost.getId();

                        // Create attachment
                        Attachment createdAttachment = DBAttachment.createAttachment(postId, file, size, name, type);

                        if (createdAttachment == null)
                            session.setAttribute("error", "Could not create attachment");

                        // TO-DO
                        // Display attachment in view
                        // Edit attachment and mark post as updated
                        // Delete attachment
                    }
                }
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

        RequestDispatcher rd = request.getRequestDispatcher("Posts.jsp");
        rd.forward(request, response);
    }
}
