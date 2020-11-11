package db;

import models.Attachment;
import models.Post;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBAttachment {
    private static String query = null;
    private static PreparedStatement st = null;

    public static Attachment getAttachment(int postId) {
        Attachment attachment = null;

        try {
            // Initialize the database
            Connection con = DBConnection.getConnection();

            // SQL query
            query = "SELECT * FROM Attachments WHERE post_id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, postId);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                attachment = new Attachment(
                        rs.getInt("attachment_id"),
                        rs.getInt("post_id"),
                        rs.getInt("size"),
                        rs.getBlob("file"),
                        rs.getString("name"),
                        rs.getString("type"));
            }

            // Close all connections
            rs.close();
            st.close();
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return attachment;
    }

    public static Attachment createAttachment(int postId, InputStream file, int size, String name, String type) {
        Attachment attachment = null;

        try {
            if (name.equals("") || type.equals(""))
                return null;

            // Initialize the database
            Connection con = DBConnection.getConnection();

            // SQL query
            query = "INSERT INTO Attachments (post_id, file, size, name, type) values(?, ?, ?, ?, ?)";
            st = con.prepareStatement(query);
            st.setInt(1, postId);
            st.setBlob(2, file);
            st.setInt(3, size);
            st.setString(4, name);
            st.setString(5, type);

            // Execute the insert command using executeUpdate() to make changes in database
            st.executeUpdate();

            // Close all the connections
            st.close();
            con.close();

            attachment = getAttachment(postId);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return attachment;
    }

    public static Attachment deleteAttachment(int postId) {
        try {
            // Initialize the database
            Connection con = DBConnection.getConnection();

            Attachment deletedAttachment = getAttachment(postId);

            // SQL query
            query = "DELETE FROM Attachments WHERE post_id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, postId);

            st.executeUpdate();

            // Close all the connections
            st.close();
            con.close();

            return deletedAttachment;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
