/**
 * Created by jubair on 10/19/17.
 */
public class CloneFragment {
	public String path;
	public int startLine;
	public int endLine;
	public int pcid;

	public CloneFragment(){
		this.path = "";
		this.startLine = 0;
		this.endLine = 0;
		this.pcid = -2;
	}

	public CloneFragment getFragment() {
		CloneFragment cloneFragment = new CloneFragment();
		cloneFragment.path = this.path;
		cloneFragment.startLine = this.startLine;
		cloneFragment.endLine = this.endLine;
		cloneFragment.pcid = this.pcid;
		return cloneFragment;
	}

	public void setFragment(String path, int startLine, int endLine, int pcid) {
		this.path = path;
		this.startLine = startLine;
		this.endLine = endLine;
		this.pcid = pcid;
	}
}
