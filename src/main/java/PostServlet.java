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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "PostServlet")
@MultipartConfig(maxFileSize = 16177215) // 16 MB
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get params
        HttpSession session = request.getSession();
        session.setAttribute("error", null);

        if (request.getParameter("request") != null) {
            int postId;

            switch (request.getParameter("request")) {
                case "create":
                    // Get all params from request
                    String title = request.getParameter("title");
                    String username = request.getParameter("username");
                    String message = request.getParameter("message");
                    Part filePart = request.getPart("file");

                    // Create post
                    Post createdPost = DBPost.createPost(title, username, message);

                    // Create attachment
                    if (createdPost == null)
                        session.setAttribute("error", "Could not create post");
                    else {
                        if (filePart.getSize() > 0) {
                            // Get file params
                            String name = filePart.getSubmittedFileName();
                            int size = (int) filePart.getSize();
                            String type = filePart.getContentType();
                            InputStream file = filePart.getInputStream();
                            postId = createdPost.getPostId();

                            // Create attachment
                            Attachment createdAttachment = DBAttachment.createAttachment(postId, file, size, name, type);

                            if (createdAttachment == null)
                                session.setAttribute("error", "Could not create attachment");
                            else
                                createdPost.setAttachment(createdAttachment);
                        }
                    }
                    break;
                case "delete":
                    postId = Integer.parseInt(request.getParameter("postId"));

                    // Verify post was deleted
                    Post deletedPost = DBPost.deletePost(postId);

                    if (deletedPost == null)
                        session.setAttribute("error", "Could not delete post");
                    break;
                case "edit":
                    postId = Integer.parseInt(request.getParameter("postId"));

                    session.setAttribute("editMessage", postId);
                    session.setAttribute("editTitle", postId);
                    session.setAttribute("updatedTime", postId);
                    break;
                case "view":
                    response.sendRedirect("PostDownload.jsp");
                    return;
                case "save":
                    String editedMessage = request.getParameter("editMessage");
                    String editedTitle = request.getParameter("editTitle");

                    if (editedMessage.equals("") || editedTitle.equals("")) {
                        session.setAttribute("error", "Could not edit post");
                    } else {
                        postId = Integer.parseInt(request.getParameter("postId"));

                        DBPost.updatePost(postId, editedMessage, editedTitle);
                        session.setAttribute("editTitle", "");
                        session.setAttribute("editMessage", "");
                    }
                    break;
            }
        }

        response.sendRedirect("/message_board_war/posts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get params
        ServletConfig config = getServletConfig();
        int limit = Integer.parseInt(config.getInitParameter("limit"));

        LinkedList<Post> posts = DBPost.getPosts(limit);

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
                posts = DBPost.getPosts(username, fromDate, toDate, hashtags);
            }
        }

        request.setAttribute("posts", posts);

        RequestDispatcher rd = request.getRequestDispatcher("Posts.jsp");
        rd.forward(request, response);
    }
}
