package utilites;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by jubair on 10/18/17.
 */
public class CloneClasses {
    private ArrayList<CloneClass> cloneClasses;

    public CloneClasses(){
        cloneClasses = new ArrayList<>();
    }
    public void readCloneClasses(String xmlFile){
        try {
            File file = new File(xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("class");

//            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                CloneClass singleClass = new CloneClass();
                Node nNode = nList.item(temp);

//                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
/*
                    System.out.println("Class id: " + eElement.getAttribute("classid"));
                    System.out.println("Number of Clones: " + eElement.getAttribute("nclones"));
                    System.out.println("Number of Lines: " + eElement.getAttribute("nlines"));
                    System.out.println("Similarity: " + eElement.getAttribute("similarity"));*/

                    singleClass.classId = Integer.parseInt(eElement.getAttribute("classid"));
                    singleClass.numOfClones = Integer.parseInt(eElement.getAttribute("nclones"));
                    singleClass.numOfLines = Integer.parseInt(eElement.getAttribute("nlines"));
                    singleClass.similarity = Integer.parseInt(eElement.getAttribute("similarity"));

                    NodeList sourceNode = eElement.getElementsByTagName("source");

                    ArrayList<CloneFragment> currentList = new ArrayList<>();
                    for (int i=0; i< sourceNode.getLength(); i++){
                        Node innerNode = sourceNode.item(i);

                        Element innerElement = (Element) innerNode;
/*

                        System.out.println("Path: " + innerElement.getAttribute("file"));
                        System.out.println("Start line: " + innerElement.getAttribute("startline"));
                        System.out.println("End line: " + innerElement.getAttribute("endline"));
                        System.out.println("PCID: " + innerElement.getAttribute("pcid"));
*/

                        CloneFragment cloneFragment = new CloneFragment();

                        cloneFragment.path = innerElement.getAttribute("file");
                        cloneFragment.startLine = Integer.parseInt(innerElement.getAttribute("startline"));
                        cloneFragment.endLine = Integer.parseInt(innerElement.getAttribute("endline"));
                        cloneFragment.pcid = Integer.parseInt(innerElement.getAttribute("pcid"));

                        currentList.add(cloneFragment);
					}
					singleClass.cloneFiles.addAll(currentList);
                }

                this.cloneClasses.add(singleClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCloneClasses(ArrayList<CloneClass> classes){
        this.cloneClasses = classes;
    }

    public ArrayList<CloneClass> getCloneClasses(){
        return this.cloneClasses;
    }

    public void printClasses(){
        for (CloneClass singleClass:
             this.cloneClasses) {
            System.out.println("Class id: " + singleClass.classId);
            System.out.println("Number of Clones: " + singleClass.numOfClones);
            System.out.println("Number of Lines: " + singleClass.numOfLines);
            System.out.println("Similarity: " + singleClass.similarity);

            for (CloneFragment singleFragment:
                 singleClass.cloneFiles) {
                System.out.println("Path: " + singleFragment.path);
                System.out.println("Start line: " + singleFragment.startLine);
                System.out.println("End line: " + singleFragment.endLine);
                System.out.println("PCID: " + singleFragment.pcid);
            }
        }
    }

    public CloneClass getCloneClass(String oneFragmentPath){
        CloneClass requiredClass = null;

        for (CloneClass singleClass:
                this.cloneClasses) {
            for (CloneFragment singleFragment:
                    singleClass.cloneFiles) {
                if (singleFragment.path.equals(oneFragmentPath))
                    requiredClass = singleClass;
            }
        }

        return requiredClass;
    }

	public static void main(String[] args) {
		CloneClasses cloneClasses = new CloneClasses();
		cloneClasses.readCloneClasses("/home/jubair/SPL3/test_projects/cloneResult/JHotDraw54b1_functions-clones/JHotDraw54b1_functions-clones-0.30-classes.xml");

//		cloneClasses.printClasses();
        CloneClass cloneClass = cloneClasses.getCloneClass("/home/jubair/SPL3/test_projects/JHotDraw54b1/src/CH/ifa/draw/application/DrawApplication.java");
        System.out.println(cloneClass.numOfClones);
    }
}
