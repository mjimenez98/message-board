import db.DBConnection;
import db.DBAttachment;
import db.DBAttachment;

import models.Attachment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "DownloadServlet")
public class DownloadServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int postId = Integer.parseInt(request.getParameter("postId"));
        Attachment attachment = DBAttachment.getAttachment(postId);

        String fileName = attachment.getName();
        System.out.println("File Name: " + fileName);

        //Gets File Type
        String contentType = this.getServletContext().getMimeType(fileName);
        System.out.println("Content Type: " + contentType);
        response.setHeader("Content-Type", contentType);
        try {
            response.setHeader("Content-Length", String.valueOf(attachment.getFile().length()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        response.setHeader("Content-Disposition", "inline; filename=\"" + attachment.getName() + "\"");

        Blob fileData = attachment.getFile();
        InputStream is = null;
        try {
            is = fileData.getBinaryStream();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        byte[] bytes = new byte[1024];
        int bytesRead;

        while ((bytesRead = is.read(bytes)) != -1) {
            response.getOutputStream().write(bytes, 0, bytesRead);
        }
        is.close();
    }
}
