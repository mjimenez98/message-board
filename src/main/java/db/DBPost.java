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
            query = "SELECT * FROM Posts ORDER BY created_at DESC LIMIT ?";
            st = con.prepareStatement(query);
            st.setInt(1, limit);

            ResultSet rs = st.executeQuery();

            // Go through every result in set, create Post object and add it to linked list
            while(rs.next()) {
                assert false;
                posts.add(new Post(
                        rs.getInt("post_id"),
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
            query = "SELECT * FROM Posts WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                post = new Post(
                        rs.getInt("post_id"),
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
            query = "SELECT * FROM Posts WHERE title = ? AND username = ? AND message = ?";
            st = con.prepareStatement(query);
            st.setString(1, title);
            st.setString(2, username);
            st.setString(3, message);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                post = new Post(
                        rs.getInt("post_id"),
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
            query = "INSERT INTO Posts (title, username, message) values(?, ?, ?)";
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
            query = "DELETE FROM Posts WHERE id = ?";
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
}
