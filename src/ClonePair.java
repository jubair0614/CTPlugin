import java.util.ArrayList;

/**
 * Created by jubair on 11/23/17.
 * Time 4:41 AM
 */
public class ClonePair {
	public int numOfLines;
	public int similarity;

	public ArrayList<CloneFragment> pairs;
	public ClonePair(){
		this.numOfLines = 0;
		this.similarity = 0;
		this.pairs = new ArrayList<>();
	}

	public ArrayList<CloneFragment> getPairs(){
		return this.pairs;
	}

}
