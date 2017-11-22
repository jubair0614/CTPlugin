package tryPackage;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import tracker.FileGenerator;

public class EditorAreaIllustration extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        int offset = caretModel.getOffset();

        PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);
        PsiElement elementAt = psiFile.findElementAt(offset).getParent();
        String changedMethod = elementAt.getText();
        System.out.println(changedMethod);

        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.setChanged(changedMethod);
    }

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setVisible(project != null && editor != null);
    }
}