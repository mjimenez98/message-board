import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class GroupsTest {
    @Test
    public void firstTest() {
        Assert.assertTrue(true);
    }


    @Test
    public void groupsExistence() {
        try {
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse("src\\main\\webapp\\WEB-INF\\groups.xml");
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList) xp.compile("//group").evaluate(d, XPathConstants.NODESET);
            System.out.println("Number of existing group: " + nl.getLength());

            for (int i= 0; i<nl.getLength(); i++ ) {
                System.out.println("Group: " + xp.compile(".//group_name").evaluate(nl.item(i)));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}