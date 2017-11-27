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

public class SelectedFragment extends AnAction {

    @Override
    public void actionPerformed(final AnActionEvent anActionEvent) {
        //Get all the required data from data keys
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);

        //Access document, caret, and selection
        final SelectionModel selectionModel = editor.getSelectionModel();

        String selectedText = selectionModel.getSelectedText();
        System.out.println(selectedText);
        selectionModel.removeSelection();

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.setOriginal(selectedText);

        PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);
        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset).getParent();
        System.out.println(elementAt.getText());

        PSIContainer.getInstance().setOriginal(elementAt);
        PSIContainer.getInstance().analyze(PSIContainer.getInstance().getOriginal());

        boolean methodFragment = checkForValidMethodFragment(selectedText);
        System.out.println(methodFragment);
    }

    private boolean checkForValidMethodFragment(String selectedText) {

        return false;
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
