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
 * Created by jubair on 11/23/17.
 * Time 4:36 AM
 */
public class ClonePairs {
	private ArrayList<ClonePair> clonePairs;

	public ClonePairs(){
		clonePairs = new ArrayList<>();
	}
	public void readClonePairs(String xmlFile){
		try {
			File file = new File(xmlFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			doc.getDocumentElement().normalize();

//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("clone");

//			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				ClonePair clonePair = new ClonePair();
				Node nNode = nList.item(temp);

//				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					/*System.out.println("Number of Lines: " + eElement.getAttribute("nlines"));
					System.out.println("Similarity: " + eElement.getAttribute("similarity"));
*/
					clonePair.numOfLines = Integer.parseInt(eElement.getAttribute("nlines"));
					clonePair.similarity = Integer.parseInt(eElement.getAttribute("similarity"));

					NodeList sourceNode = eElement.getElementsByTagName("source");

					ArrayList<CloneFragment> clones = new ArrayList<>();

					for (int i=0; i<sourceNode.getLength(); i++){

						CloneFragment cloneFragment = new CloneFragment();

						Node innerNode = sourceNode.item(i);
						Element innerElement = (Element) innerNode;


						cloneFragment.path = innerElement.getAttribute("file");
						cloneFragment.startLine = Integer.parseInt(innerElement.getAttribute("startline"));
						cloneFragment.endLine = Integer.parseInt(innerElement.getAttribute("endline"));
						cloneFragment.pcid = Integer.parseInt(innerElement.getAttribute("pcid"));

						clones.add(cloneFragment);
					}

					clonePair.pairs.addAll(clones);
				}
				this.clonePairs.add(clonePair);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setClonePairs(ArrayList<ClonePair> pairs){
		this.clonePairs = pairs;
	}

	public ArrayList<ClonePair> getClonePairs(){
		return this.clonePairs;
	}

	public void printPairs(){
		for (ClonePair currentPair
				: this.clonePairs) {
			System.out.println("Number of Lines: " + currentPair.numOfLines);
			System.out.println("Similarity: " + currentPair.similarity);

			for (CloneFragment singleFragment:
					currentPair.pairs) {
				System.out.println("Path: " + singleFragment.path);
				System.out.println("Start line: " + singleFragment.startLine);
				System.out.println("End line: " + singleFragment.endLine);
				System.out.println("PCID: " + singleFragment.pcid);
			}
		}
	}

	public ClonePair getClonePair(String oneFragmentPath){
		ClonePair requiredPair = null;

		for (ClonePair singlePair:
				this.clonePairs) {
			for (CloneFragment singleFragment:
					singlePair.pairs) {
				if (singleFragment.path.equals(oneFragmentPath))
					requiredPair = singlePair;
			}
		}

		return requiredPair;
	}

	public int getNumOfPairs(){
		return this.clonePairs.size();
	}

	public static void main(String[] args) {
		ClonePairs clonePairs = new ClonePairs();
		clonePairs.readClonePairs("/home/jubair/SPL3/test_projects/cloneResult/JHotDraw54b1_functions-clones/JHotDraw54b1_functions-clones-0.30.xml");
		System.out.println(clonePairs.getNumOfPairs());

		clonePairs.printPairs();
		ClonePair clonePair = clonePairs.getClonePair("/home/jubair/SPL3/test_projects/JHotDraw54b1/src/CH/ifa/draw/application/DrawApplication.java");
		System.out.println(clonePair.similarity);
	}
}
