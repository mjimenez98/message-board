import com.google.common.hash.Hashing;
import models.Group;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
//Source: https://www.journaldev.com/1907/java-session-management-servlet-httpsession-url-rewriting#servlet-url-rewriting

@WebServlet(name = "LoginPageServlet")
public class LoginPageServlet extends HttpServlet {
    UserManagerImpl userManagerImpl;

    @Override
    public void init() throws ServletException {
        UserManagerEnum userManager = UserManagerEnum.INSTANCE;
        userManager.setUserManagerImplementation(getServletConfig().getInitParameter("userManagerImplClass"), getServletContext().getRealPath("/WEB-INF/memberships.xml"), getServletContext().getRealPath("/WEB-INF/groups.xml"), getServletContext().getRealPath("/WEB-INF/users.xml"));
        userManagerImpl = userManager.getUserManagerImplementation();
    }

    private static final int MAX_INACTIVE = 30 * 60;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userManagerImpl.isAuthenticated(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", username);

            // Setting session to expiry in 30 mins
            session.setMaxInactiveInterval(MAX_INACTIVE);
            List<Group> memberships = userManagerImpl.getGroupMemberships(username);
            session.setAttribute("memberships", memberships);
            //Get the encoded URL string
            String encodedURL = response.encodeRedirectURL("/message_board_war/posts");
            response.sendRedirect(encodedURL);
        } else {
            request.setAttribute("loginError", "The email and password combination is incorrect!");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login.jsp");
            rd.include(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
