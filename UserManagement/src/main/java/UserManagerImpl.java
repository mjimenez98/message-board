import com.google.common.hash.Hashing;
import models.Group;
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
import java.util.ArrayList;
import java.util.List;

public class UserManagerImpl implements UserManager {
    String userMembershipFile; //TO DO: rename
    String groupsFile;
    String usersFile;

    public UserManagerImpl(String userMembership, String groupsFile, String usersFile){
        this.userMembershipFile = userMembership;
        this.groupsFile = groupsFile;
        this.usersFile = usersFile;
    } //Pass user membership file

    /**
     * Returns all the groups the member is part of explicitly.
     * @param user
     * @return
     */
    @Override
    public List<Group> getGroupMemberships(String user) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document membershipDoc = db.parse(userMembershipFile);
            XPath xp = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xp.compile("//user-membership[user_id= '" + user + "']/group_name/text()");
            NodeList nodes = (NodeList) expr.evaluate(membershipDoc, XPathConstants.NODESET);
            List<Group> memberships = new ArrayList<>();
            for (int i = 0; i < nodes.getLength(); i++) {
                String group = nodes.item(i).getNodeValue();
                Group membership = new Group(group, groupsFile);
                memberships.add(membership);
            }
            return memberships;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isAuthenticated(String username, String password) {
        //Build DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            FileInputStream fileIS = new FileInputStream(usersFile);
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
