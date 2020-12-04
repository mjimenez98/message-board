import com.google.common.hash.Hashing;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserManagerImpl implements UserManager {
    String file1; //TO DO: rename

    public UserManagerImpl(String file1, String file2){
        this.file1 = file1;
    }

    @Override
    public List<String> getGroupMemberships(String user) {
        //TO DO: Add implementation
        return null;
    }

    @Override
    public boolean isAuthenticated(String username, String password) {
        //Build DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            FileInputStream fileIS = new FileInputStream(file1);
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
}
