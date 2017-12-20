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
import tracker.PSIContainer;
import utilites.*;

import java.util.ArrayList;

public class SelectedFragment extends AnAction {
    public CloneFragment cloneFragment;
    @Override
    public void actionPerformed(final AnActionEvent anActionEvent) {
        //Get all the required data from data keys
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);

        //Access document, caret, and selection
        final SelectionModel selectionModel = editor.getSelectionModel();

        String selectedText = selectionModel.getSelectedText();
        //System.out.println(selectedText);
        selectionModel.removeSelection();

        int startLine = selectionModel.getSelectionStartPosition().getLine()+1;
        int endLine = selectionModel.getSelectionEndPosition().getLine()+1;


        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.setOriginal(selectedText);

        PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);
        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset).getParent();
        //System.out.println(elementAt.getText());

        String fragmentPath = psiFile.getContainingDirectory().toString() + "/" + psiFile.getName();
        fragmentPath = fragmentPath.substring(13, fragmentPath.length());
        System.out.println(fragmentPath);


//        PSIContainer.getInstance().setOriginal(elementAt);
//        PSIContainer.getInstance().analyze(PSIContainer.getInstance().getOriginal());

        CloneClass cloneClass = checkForClonedFragment(fragmentPath, startLine, endLine);
        if(cloneClass!=null) {
            System.out.println(cloneClass.cloneFiles.size());
            CurrentClassContainer.getInstance().setCloneClass(cloneClass);
            CurrentClassContainer.getInstance().setCurrentFragment(this.cloneFragment);
        }
    }

    public CloneClass checkForClonedFragment(String path, int start, int end) {
        ArrayList<CloneClass> cloneClasses = CloneClasses.getCloneClasses();
        for (CloneClass cloneclass :
                cloneClasses) {
            for (CloneFragment fragment :
                    cloneclass.cloneFiles) {
                if (fragment.path.equals(path) && fragment.startLine == start) {
                    this.cloneFragment = fragment;
                    return cloneclass;
                }
            }
        }
        return null;
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
