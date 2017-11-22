package tryPackage;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class EditorAreaIllustration extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        LogicalPosition logicalPosition = caretModel.getLogicalPosition();
        VisualPosition visualPosition = caretModel.getVisualPosition();
        int offset = caretModel.getOffset();

        PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);
        PsiElement elementAt = psiFile.findElementAt(offset).getParent();
        String text = psiFile.findElementAt(offset).getNode().getPsi().getText();
        System.out.println(text);
        String s = elementAt.getNode().getElementType().toString();
        System.out.println(s);
        System.out.println(elementAt.getText());

        Messages.showInfoMessage(logicalPosition.toString() + "\n" +
                visualPosition.toString() + "\n" +
                "Offset: " + offset, "Caret Parameters Inside The Editor");
    }

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setVisible(project != null && editor != null);
    }
}