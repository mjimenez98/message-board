package db;

import models.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class DBPost {
    private static String query = null;
    private static PreparedStatement st = null;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LinkedList<Post> getPosts(int limit) {
        LinkedList<Post> posts = new LinkedList<>();

        try {
            // Initialize the database
            Connection con = DBConnection.getConnection();

            // SQL query
            query = "SELECT * FROM post ORDER BY created_at DESC LIMIT ?";
            st = con.prepareStatement(query);
            st.setInt(1, limit);

            ResultSet rs = st.executeQuery();

            // Go through every result in set, create Post object and add it to linked list
            while(rs.next()) {
                assert false;
                posts.add(new Post(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("username"),
                        LocalDateTime.parse(rs.getString("created_at"), formatter),
                        LocalDateTime.parse(rs.getString("updated_at"), formatter),
                        rs.getString("message")));
            }

            // Close all connections
            rs.close();
            st.close();
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return posts;
    }

    public static Post getPost(int id) {
        Post post = null;

        try {
            // Initialize the database
            Connection con = DBConnection.getConnection();

            // SQL query
            query = "SELECT * FROM post WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                post = new Post(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("username"),
                        LocalDateTime.parse(rs.getString("created_at"), formatter),
                        LocalDateTime.parse(rs.getString("updated_at"), formatter),
                        rs.getString("message"));
            }

            // Close all connections
            rs.close();
            st.close();
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return post;
    }

    public static Post getPost(String title, String username, String message) {
        Post post = null;

        try {
            // Initialize the database
            Connection con = DBConnection.getConnection();

            // SQL query
            query = "SELECT * FROM post WHERE title = ? AND username = ? AND message = ?";
            st = con.prepareStatement(query);
            st.setString(1, title);
            st.setString(2, username);
            st.setString(3, message);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                post = new Post(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("username"),
                        LocalDateTime.parse(rs.getString("created_at"), formatter),
                        LocalDateTime.parse(rs.getString("updated_at"), formatter),
                        rs.getString("message"));
            }

            // Close all connections
            rs.close();
            st.close();
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return post;
    }

    public static Post createPost(String title, String username, String message) {
        Post post = null;

        try {
            if (title.equals("") || username.equals("") || message.equals(""))
                return null;

            // Initialize the database
            Connection con = DBConnection.getConnection();

            // SQL query
            query = "INSERT INTO post (title, username, message) values(?, ?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, title);
            st.setString(2, username);
            st.setString(3, message);

            // Execute the insert command using executeUpdate() to make changes in database
            st.executeUpdate();

            // Close all the connections
            st.close();
            con.close();

            post = getPost(title, username, message);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return post;
    }

    public static Post deletePost(int id) {
        try {
            // Initialize the database
            Connection con = DBConnection.getConnection();

            Post deletedPost = getPost(id);

            // SQL query
            query = "DELETE FROM post WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);

            st.executeUpdate();

            // Close all the connections
            st.close();
            con.close();

            return deletedPost;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void updatePost(int idPost, String editedMessage,String editedTitle,LocalDateTime updatedTime) {
        try {

            // Initialize the database
            Connection con = DBConnection.getConnection();

            // SQL query
            query = "UPDATE post SET message = ?, title = ?, updated_at = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setString(1, editedMessage);
            st.setString(2, editedTitle);
            st.setString(3, String.valueOf(updatedTime));
            st.setString(4, String.valueOf(idPost));

            // Execute the insert command using executeUpdate() to make changes in database
            st.executeUpdate();

            // Close all the connections
            st.close();
            con.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

