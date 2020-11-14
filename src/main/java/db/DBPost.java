package db;

import models.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

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
            while (rs.next()) {
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
        } catch (Exception e) {
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

            while (rs.next()) {
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
        } catch (Exception e) {
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

            while (rs.next()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return post;
    }

    public static LinkedList<Post> getPosts(String user, String fromDate, String toDate, String[] hashtags) {
        LinkedList<Post> posts = new LinkedList<>();

        try {
            // Initialize the database
            Connection con = DBConnection.getConnection();
            Map<Integer, String> statementParams = new HashMap<>();
            // SQL query
            query = "SELECT * FROM post";

            boolean firstCondition = true;
            int currentParamIndex = 1;
            if (user != null && user != "") {
                firstCondition = false;
                query += " WHERE ";
                query += " username = ?";
                statementParams.put(currentParamIndex, user);
                currentParamIndex++;
            }
            if (fromDate != null) {
                query += firstCondition ? " WHERE " : " AND ";
                if (firstCondition) {
                    firstCondition = false;
                }
                query += " created_at >= ? ";
                statementParams.put(currentParamIndex, fromDate);
                currentParamIndex++;
            }
            if (toDate != null) {
                query += firstCondition ? " WHERE " : " AND ";
                if (firstCondition) {
                    firstCondition = false;
                }
                query += " created_at <= ? ";
                statementParams.put(currentParamIndex, toDate);
                currentParamIndex++;
            }
            if (hashtags[0] != "") {
                query += firstCondition ? " WHERE " : " AND ";
                query += "(message LIKE ?";
                statementParams.put(currentParamIndex, hashtags[0]);
                currentParamIndex++;
                for (int i = 1; i < hashtags.length; i++) {
                    query += " OR message LIKE ? ";
                    statementParams.put(currentParamIndex, hashtags[i]);
                    currentParamIndex++;
                }
                query += ")";
            }
            query += " ORDER BY created_at DESC";

            st = con.prepareStatement(query);

            Iterator it = statementParams.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                st.setString((int) pair.getKey(), (String) pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }

            ResultSet rs = st.executeQuery();

            // Go through every result in set, create Post object and add it to linked list
            while (rs.next()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
