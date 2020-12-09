package models;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Group {
    String groupsFile;
    String groupName;
    List<String> parents;
    Boolean hasCircularDependency=false;

    public Group(String name, String groupsFile){
        groupName = name;
        this.groupsFile = groupsFile;
        setParents(findParents(name));
    }

    public Boolean getHasCircularDependency() {
        return hasCircularDependency;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getParents() {
        return parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    private List<String> findParents(String groupName){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            FileInputStream membershipsFile = new FileInputStream(groupsFile);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document groupsDoc = db.parse(new FileInputStream(groupsFile));
            String givenGroup = groupName;
            String currentParent = groupName;
            List<String> parents = new ArrayList<>();
            while (!currentParent.equals("") ){
                try {
                    currentParent = getParent(currentParent, groupsDoc);
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
                if (currentParent.equals(groupName)) {
                    hasCircularDependency = true;
                    break;
                }
                if (!currentParent.equals("")) {
                    parents.add(currentParent);
                }
            }
            return parents;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getParent(String groupName, Document groupsDoc) throws XPathExpressionException {
        String parent = "";
        if(groupName != "") {
            XPath xp = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xp.compile("//group[group_name= '" + groupName + "']/parent/text()");
            NodeList nodes = (NodeList) expr.evaluate(groupsDoc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                parent = nodes.item(i).getNodeValue();
            }
        }
        return parent;
    }

    public boolean checkCircularDependency(){
        return parents.contains(groupName);
    }
}