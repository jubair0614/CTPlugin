package tracker;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiModificationTracker;

/**
 * Created by jubair on 11/26/17.
 * Time 3:41 PM
 */
public class PSIContainer {

	private static PSIContainer psiContainer = null;

	private PsiElement original;
	private PsiElement changed;

	public PSIContainer(){
		original = null;
		changed = null;
	}

	public static PSIContainer getInstance(){
		if(psiContainer == null){
			psiContainer = new PSIContainer();
		}
		return psiContainer;
	}

	public PsiElement getOriginal() {
		return original;
	}

	public void setOriginal(PsiElement original) {
		this.original = original;
	}

	public PsiElement getChanged() {
		return changed;
	}

	public void setChanged(PsiElement changed) {
		this.changed = changed;
	}

	public void analyze(PsiElement current){
		PsiElement[] children = current.getChildren();
		for (PsiElement child:
			 children) {
			System.out.println("CHILD: " + child);
			System.out.println("TEXT: " + child.getText());
		}
	}

	public void compare(){
		if(original != null && changed != null){
			PsiElement[] originalChildren = original.getChildren();
			PsiElement[] changedChildren = changed.getChildren();
			
		}
	}

}
