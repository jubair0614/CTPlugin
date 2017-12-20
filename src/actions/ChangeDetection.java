package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import tracker.FileGenerator;
import tracker.JavaDiffUtils;
import tracker.PSIContainer;
import utilites.*;
import views.ClonedCodeViewUpdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ChangeDetection extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent anActionEvent) {
		//Get all the required data from data keys
		final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);

		//Access document, caret, and selection
		final SelectionModel selectionModel = editor.getSelectionModel();

		String selectedText = selectionModel.getSelectedText();
		System.out.println(selectedText);
		selectionModel.removeSelection();

		FileGenerator.getInstance().setChanged(selectedText);

		JavaDiffUtils diffU = new JavaDiffUtils();
		diffU.run();

		CloneClass cloneClass = CurrentClassContainer.getInstance().getCloneClass();
		ArrayList<CodeFragment> allCodeFragments = CodeFragments.getInstance().getCodeFragments();

		ArrayList<CodeFragment> codeFragments = new ArrayList<>();

		for (CodeFragment codeFragment:
				allCodeFragments) {
			for (CloneFragment cloneFragment:
					cloneClass.cloneFiles) {
				if(codeFragment.getCloneFragment().path == cloneFragment.path && codeFragment.getCloneFragment().startLine == cloneFragment.startLine && codeFragment.getCloneFragment().endLine == cloneFragment.endLine){
					codeFragments.add(codeFragment);
				}
			}
		}

		ArrayList<String[]> changeList= new ArrayList<>();
		codeFragments.forEach(codeFragment -> {
			String [] temp = {codeFragment.getCloneFragment().path, codeFragment.getContent()};
			changeList.add(temp);
		});

		ClonedCodeViewUpdate codeViewUpdate = ToolWindowRepository.getInstance().getCodeView();

		codeViewUpdate.changedSelectedFragment(selectedText);
		codeViewUpdate.changeCloneList(changeList);

		/*if(cloneClass != null && cloneClass.cloneFiles.size() > 0){
			CloneFragment currentFragment = CurrentClassContainer.getInstance().getCurrentFragment();
			for (CloneFragment cloneFragment:
					cloneClass.cloneFiles) {
				if(currentFragment.equals(cloneFragment)) continue;
				System.out.println(cloneFragment.path + " " + cloneFragment.startLine + " " + cloneFragment.endLine);
			}
		}*/
		//CloneFragment cloneFragment = CloneClasses.validClone(fragmentPath, position);
		/*if(cloneFragment != null) {
			CodeFragment fragmentWithContent = CodeFragments.getInstance().getFragmentWithContent(cloneFragment);
			if(fragmentWithContent != null){
				FileGenerator.getInstance().setOriginal(fragmentWithContent.getContent());
			}
		}*/

		/*PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);
		int offset = editor.getCaretModel().getOffset();
		PsiElement elementAt = psiFile.findElementAt(offset).getParent();
		System.out.println(elementAt.getText());

		FileGenerator.getInstance().setChanged(selectedText);
		diffU.run();*/

		/*PSIContainer.getInstance().setChanged(elementAt);
		PSIContainer.getInstance().analyze(PSIContainer.getInstance().getChanged());
		PSIContainer.getInstance().compare();*/
	}

	@Override
	public void update(final AnActionEvent e) {
		//Get required data keys
		final Project project = e.getData(CommonDataKeys.PROJECT);
		final Editor editor = e.getData(CommonDataKeys.EDITOR);
		//Set visibility only in case of existing project and editor and if some text in the editor is selected
		e.getPresentation().setVisible((project != null && editor != null && editor.getSelectionModel().hasSelection()));
	}
}
