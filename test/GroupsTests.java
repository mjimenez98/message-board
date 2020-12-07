import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class GroupsTests {
    @Test
    public void firstTest() {
        Assert.assertTrue(true);
    }

    @Test
    public void groupsExistence() {
        try {
            String groupNameExpected = "admins\n" +
                                "concordia\n" +
                                "encs\n" +
                                "comp\n" +
                                "soen\n";
            String groupNameXML ="";

            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse("src\\main\\webapp\\WEB-INF\\groups.xml");
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList) xp.compile("//group").evaluate(d, XPathConstants.NODESET);

            for (int i= 0; i<nl.getLength(); i++ ) {
                groupNameXML= groupNameXML + xp.compile(".//group_name").evaluate(nl.item(i)) + "\n";
            }
                Assert.assertEquals(groupNameExpected,groupNameXML);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void UndefinedGroup() {
        try {
            String undefinedGroup = "engr";
            String groupNameXML ="";

            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse("src\\main\\webapp\\WEB-INF\\groups.xml");
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList) xp.compile("//group").evaluate(d, XPathConstants.NODESET);

            for (int i= 0; i<nl.getLength(); i++ ) {
                groupNameXML= groupNameXML + xp.compile(".//group_name").evaluate(nl.item(i)) + "\n";
            }
            Assert.assertFalse(groupNameXML.contains(undefinedGroup));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}