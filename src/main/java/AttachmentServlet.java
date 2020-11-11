import db.DBAttachment;
import db.DBPost;
import models.Attachment;
import models.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

// TO-DO
// Edit attachment and mark post as updated

@WebServlet(name = "AttachmentServlet")
@MultipartConfig(maxFileSize = 16177215) // 16 MB
public class AttachmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("error", null);

        int postId = Integer.parseInt(request.getParameter("postId"));
        Part filePart = request.getPart("file");

        if (request.getParameter("request") != null) {
            if (request.getParameter("request").equals("update")) {
                if (filePart.getSize() > 0) {
                    String name = filePart.getSubmittedFileName();
                    int size = (int) filePart.getSize();
                    String type = filePart.getContentType();
                    InputStream file = filePart.getInputStream();

                    // Update attachment
                    Attachment updatedAttachment = DBAttachment.updateAttachment(postId, file, size, name, type);

                    // Update associated post
                    if (updatedAttachment.getName().equals(name) && updatedAttachment.getSize() == size) {
                        Post post = DBPost.getPost(postId);
                        post.setAttachment(updatedAttachment);
                    }
                    else {
                        session.setAttribute("error", "Could not update attachment");
                    }
                }
            } else if (request.getParameter("request").equals("delete")) {
                DBAttachment.deleteAttachment(postId);
                Attachment attachment = DBAttachment.getAttachment(postId);

                if (attachment != null)
                    session.setAttribute("error", "Could not delete attachment");
            }
        }

        response.sendRedirect("/message_board_war/posts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
