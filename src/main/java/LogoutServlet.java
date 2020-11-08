import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
//Source: https://www.journaldev.com/1907/java-session-management-servlet-httpsession-url-rewriting#servlet-url-rewriting

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        //no encoding because we have invalidated the session
        response.sendRedirect("/message_board_war/Login.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
