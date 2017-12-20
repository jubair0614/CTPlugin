package utilites;

import com.intellij.psi.PsiElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by jubair on 12/18/17.
 * Time 3:11 PM
 */
public class CodeFragment {
	CloneFragment cloneFragment;
	String content;


	public CodeFragment(){
		this.cloneFragment = null;
		this.content = "";
	}

	public CodeFragment getFragment() {
		CodeFragment codeFragment = new CodeFragment();
		codeFragment.cloneFragment = this.cloneFragment;
		codeFragment.content = this.content;
		return codeFragment;
	}

	public void setFragment(CloneFragment cloneFragment, String content){
		this.cloneFragment = cloneFragment;
		this.content = content;
	}



	public String getContent(){
		return this.content;
	}

	public CloneFragment getCloneFragment(){
		return this.cloneFragment;
	}


	/*public void setContent(CloneFragment cloneFragment, String content, PsiElement psiContent){
		this.cloneFragment = cloneFragment;
		this.content = content;
		this.psiContent = psiContent;
	}*/
}
