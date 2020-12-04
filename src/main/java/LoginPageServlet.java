import com.google.common.hash.Hashing;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
//Source: https://www.journaldev.com/1907/java-session-management-servlet-httpsession-url-rewriting#servlet-url-rewriting

@WebServlet("/LoginPageServlet")
public class LoginPageServlet extends HttpServlet {
    UserManagerImpl userManagerImpl;

    @Override
    public void init() throws ServletException {
        UserManagerEnum userManager = UserManagerEnum.INSTANCE;
        userManager.setUserManagerImplementation(getServletContext().getInitParameter("userManagerImpl"), getServletContext().getRealPath("/WEB-INF/users.xml"));
        userManagerImpl = userManager.getUserManagerImplementation();
    }

    private static final int MAX_INACTIVE = 30 * 60;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (userManagerImpl.isAuthenticated(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", username);
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(MAX_INACTIVE);
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
