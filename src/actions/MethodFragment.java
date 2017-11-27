package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

public class MethodFragment extends AnAction {

    PsiClass psiClass = null;
    @Override
    public void actionPerformed(AnActionEvent e) {
        this.psiClass = getPsiClassFromContext(e);

        PsiMethod[] psiClassMethods = psiClass.getMethods();
        printMethodNames(psiClassMethods);
        System.out.println(psiClassMethods.length);
        printMethodBody(psiClassMethods);
        getDirectory();
    }

    private void printMethodBody(PsiMethod[] psiClassMethods) {
        for (PsiMethod psiMethod:
                psiClassMethods) {
            PsiCodeBlock body = psiMethod.getBody();
            System.out.println(body.getText());
        }
    }

    private String getDirectory(){
        System.out.println(this.psiClass.getContainingFile().getContainingDirectory());
        return this.psiClass.getContainingFile().getContainingDirectory().toString();
    }

    private void printMethodNames(PsiMethod[] psiClassMethods) {
        for (PsiMethod psiMethod:
                psiClassMethods) {
            System.out.println(psiMethod.getName());
        }
    }

    public void update(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        e.getPresentation().setEnabled(psiClass != null);
    }

    public PsiClass getPsiClassFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        if(psiFile == null || editor == null){
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
        return psiClass;
    }
}
