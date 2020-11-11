package db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DBAttachment {
    private static String query = null;
    private static PreparedStatement st = null;

    public static void createAttachment(int postId, InputStream file, int size, String filename, String contentType) {
        try {
            // Initialize the database
            Connection con = DBConnection.getConnection();

            // SQL query
            query = "INSERT INTO Attachments (post_id, file, size, name, type) values(?, ?, ?, ?, ?)";
            st = con.prepareStatement(query);
            st.setInt(1, postId);
            st.setBlob(2, file);
            st.setInt(3, size);
            st.setString(4, filename);
            st.setString(5, contentType);

            // Execute the insert command using executeUpdate() to make changes in database
            st.executeUpdate();

            // Close all the connections
            st.close();
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
