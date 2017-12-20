package utilites;

/**
 * Created by jubair on 12/19/17.
 * Time 4:35 PM
 */
public class CurrentClassContainer {
	private static CurrentClassContainer currentClassContainer = null;
	CloneClass cloneClass;
	CloneFragment cloneFragment;


	private CurrentClassContainer() {

	}

	public static CurrentClassContainer getInstance(){
		if(currentClassContainer == null){
			currentClassContainer = new CurrentClassContainer();
		}
		return currentClassContainer;
	}

	public void setCloneClass(CloneClass cloneClass){
		this.cloneClass = cloneClass;
	}

	public CloneClass getCloneClass(){
		return cloneClass;
	}

	public void setCurrentFragment(CloneFragment cloneFragment) {
		this.cloneFragment = cloneFragment;
	}

	public CloneFragment getCurrentFragment() {
		return cloneFragment;
	}
}
