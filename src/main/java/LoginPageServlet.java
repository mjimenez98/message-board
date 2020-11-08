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

    private static final int MAX_INACTIVE = 30 * 60;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (isAuthenticated(username, password)) {
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

    private boolean isAuthenticated(String username, String password) {
        //Build DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            FileInputStream fileIS = new FileInputStream(getServletContext().getRealPath("/WEB-INF/users.xml"));
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xmlDocument = builder.parse(fileIS);
            //Create Xpath
            XPathFactory xpathfactory = XPathFactory.newInstance();
            XPath xpath = xpathfactory.newXPath();
            XPathExpression expr = xpath.compile("//user[user_id = '" + username + "' and password = '" + getHashedPassword(password) + "']");
            Object result = expr.evaluate(xmlDocument, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            return nodes.getLength() == 1;
        } catch (ParserConfigurationException | XPathExpressionException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getHashedPassword(String originalString) {
        String sha256hex = Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
