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
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

@WebServlet(name = "AttachmentServlet")
@MultipartConfig(maxFileSize = 16177215) // 16 MB
public class AttachmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get session params
        HttpSession session = request.getSession();
        session.setAttribute("error", null);

        // Get request params
        int postId = Integer.parseInt(request.getParameter("postId"));
        Part filePart = request.getPart("file");

        if (request.getParameter("request") != null) {
            if (request.getParameter("request").equals("update")) {
                if (filePart.getSize() > 0) {
                    // Get file params
                    String name = filePart.getSubmittedFileName();
                    int size = (int) filePart.getSize();
                    String type = filePart.getContentType();
                    InputStream file = filePart.getInputStream();

                    // Update attachment
                    Attachment updatedAttachment = DBAttachment.updateAttachment(postId, file, size, name, type);

                    // Update associated post
                    if (!updatedAttachment.getName().equals(name) || updatedAttachment.getSize() != size) {
                        session.setAttribute("error", "Could not update attachment");
                    }
                }
            } else if (request.getParameter("request").equals("delete")) {
                Attachment deletedAttachment = DBAttachment.deleteAttachment(postId);

                if (deletedAttachment == null)
                    session.setAttribute("error", "Could not delete attachment");
            }
            else if (request.getParameter("request").equals("download")){
                Attachment attachment = DBAttachment.getAttachment(postId);

                String fileName = attachment.getName();
                System.out.println("File Name: " + fileName);

                //Gets File Type
                String contentType = "application/octet-stream";
                System.out.println("Content Type: " + contentType);
                response.setHeader("Content-Type", contentType);
                response.setHeader("Content-Disposition", "inline; filename=\"" + attachment.getName() + "\"");

                try {
                    response.setHeader("Content-Length", String.valueOf(attachment.getFile().length()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                Blob fileData = attachment.getFile();
                InputStream is = null;
                OutputStream outputStream = response.getOutputStream();
                try {
                    is = fileData.getBinaryStream();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                byte[] bytes = new byte[1024];
                int bytesRead;

                while ((bytesRead = is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, bytesRead);
                }
                is.close();
                outputStream.flush();
                outputStream.close();
            }
        }

        response.sendRedirect("/message_board_war/posts");
    }
}
