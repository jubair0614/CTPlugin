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
	//PsiElement psiContent;

	public CodeFragment(){
		this.cloneFragment = null;
		this.content = "";
		//this.psiContent = null;
	}

	public CodeFragment getFragment() {
		CodeFragment codeFragment = new CodeFragment();

		return codeFragment;
	}

	public void setFragment(CloneFragment cloneFragment, String content){
		this.cloneFragment = cloneFragment;
		this.content = content;
	}


	/*public void setContent(CloneFragment cloneFragment, String content, PsiElement psiContent){
		this.cloneFragment = cloneFragment;
		this.content = content;
		this.psiContent = psiContent;
	}*/
}
