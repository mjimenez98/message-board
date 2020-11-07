import db.DBConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "PostServlet")
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all params from request
        String title = request.getParameter("title");
        String username = request.getParameter("username");
        String message = request.getParameter("message");

        try {
            // Initialize the database
            Connection con = DBConnection.getConnection();

            // SQL query
            String query = "INSERT INTO post (title, username, message) values(?, ?, ?)";

            // Create a SQL query to insert data into post table
            PreparedStatement st = con.prepareStatement(query);

            st.setString(1, title);
            st.setString(2, username);
            st.setString(3, message);

            // Execute the insert command using executeUpdate() to make changes in database
            st.executeUpdate();

            // Close all the connections
            st.close();
            con.close();

            // Get a writer pointer to display the successful result
            response.getWriter().println("<html><body><b>Successfully Inserted"+"</b></body></html>");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("Posts.jsp");
        rd.forward(request, response);
    }
}
