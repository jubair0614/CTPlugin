import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by jubair on 10/18/17.
 */
public class CloneClassBuilder {

    public void readCloneClasses(String xmlFile){
        try {
            File file = new File(xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("class");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("Class id : " + eElement.getAttribute("classid"));
                    System.out.println("Number of Clones : " + eElement.getAttribute("nclones"));
                    System.out.println("Number of Lines: " + eElement.getAttribute("nlines"));
                    System.out.println("Similarity: " + eElement.getAttribute("similarity"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CloneClassBuilder cloneClassBuilder = new CloneClassBuilder();
        cloneClassBuilder.readCloneClasses("/home/jubair/SPL3/test_projects/cloneResult/JHotDraw54b1_functions-clones");
    }
}
