import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.util.ArrayList;
import java.util.List;


public class MembershipTests {
    List<String> userActual = new ArrayList<>();
    List<String> groupsActual = new ArrayList<>();

    @Before
    public void getUsers() {
        try {
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse("src\\main\\webapp\\WEB-INF\\users.xml");

            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList) xp.compile("//user").evaluate(d, XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); i++) {
                userActual.add(xp.compile(".//user_id").evaluate(nl.item(i)) + "\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    @Before
    public void getGroups() {
        try {
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dd = db.parse("src\\main\\webapp\\WEB-INF\\groups.xml");

            XPath xpp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList) xpp.compile("//group").evaluate(dd, XPathConstants.NODESET);


            for (int i = 0; i < nl.getLength(); i++) {
                groupsActual.add(xpp.compile(".//group_name").evaluate(nl.item(i)) + "\n");
            }
            //Assert.assertEquals("These groups exists.",groupNameExpected,groupNameXML);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    @Test
    public void MembershipUndefinedUser() {
        try {
            List<String> usersExpected = new ArrayList<>();

            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse("src\\main\\webapp\\WEB-INF\\membership.xml");

            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList) xp.compile("//user-membership").evaluate(d, XPathConstants.NODESET);

            for (int i = 0; i < nl.getLength(); i++) {
                usersExpected.add(xp.compile(".//user_id").evaluate(nl.item(i)) + "\n");
            }
            usersExpected.add("Joe"); //add undefined user
            Assert.assertFalse(userActual.contains(usersExpected));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void MembershipUndefinedGroup() {
        try {
            List<String> groupsExpected = new ArrayList<>();

            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse("src\\main\\webapp\\WEB-INF\\membership.xml");

            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList) xp.compile("//user-membership").evaluate(d, XPathConstants.NODESET);

            for (int i = 0; i < nl.getLength(); i++) {
                groupsExpected.add(xp.compile(".//group_name").evaluate(nl.item(i)) + "\n");
            }
            groupsExpected.add("engr"); //add undefined group
            Assert.assertFalse(groupsActual.contains(groupsExpected));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}