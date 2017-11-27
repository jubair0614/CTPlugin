package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import tracker.PSIContainer;
import utilites.CloneClass;
import utilites.CloneClasses;
import utilites.CloneFragment;
import utilites.ToolWindowRepository;

import java.io.File;
import java.util.ArrayList;

public class CaretPositionFragment extends AnAction {

    String fragmentPath;
    int startLine;
    int endLine;

    public CaretPositionFragment(){
        this.fragmentPath = null;
        startLine = -1;
        endLine = -1;
    }

    static {
        final EditorActionManager actionManager = EditorActionManager.getInstance();
        final TypedAction typedAction = actionManager.getTypedAction();
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        int offset = caretModel.getOffset();

        PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);

        PsiElement elementAt = psiFile.findElementAt(offset).getParent();
        String fragment = elementAt.getText();
        System.out.println(fragment);

        PSIContainer.getInstance().setOriginal(elementAt);
        PSIContainer.getInstance().analyze(PSIContainer.getInstance().getOriginal());

        fragmentPath = psiFile.getContainingDirectory().toString() + "/" + psiFile.getName();
        System.out.println(fragmentPath);
        fragmentPath = fragmentPath.substring(13, fragmentPath.length());

        TextRange textRange = elementAt.getTextRange();
        VisualPosition startLinePosition = editor.offsetToVisualPosition(textRange.getStartOffset());
        VisualPosition endLinePosition = editor.offsetToVisualPosition(textRange.getEndOffset());

        this.startLine = startLinePosition.line + 1;
        this.endLine = endLinePosition.line + 1;
        System.out.println("startLine: " + this.startLine + " " + "endLine: " + this.endLine);

        CloneClass cloneClass = CloneClasses.getCloneClass(fragmentPath, startLine, endLine);
        updateToolWindow(cloneClass);
    }

    private void updateToolWindow(CloneClass cloneClass) {
        ArrayList<String[]> arrayList = new ArrayList<>();
        // return cloneClass;
        if(cloneClass != null){
            for (CloneFragment cloneFragment: cloneClass.cloneFiles) {
                arrayList.add(new String[]{new File(cloneFragment.path).getName(), Integer.toString(cloneFragment.startLine) , Integer.toString(cloneFragment.endLine), cloneFragment.path});
                System.out.println("Path: " + cloneFragment.path + " startLine: " + cloneFragment.startLine + " endLine: " + cloneFragment.endLine);
            }
        }
        else System.out.println("Not found the specified clone");

        ViewUpdateListener view= ToolWindowRepository.getInstance().getView();
        view.update(arrayList);
    }

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setVisible(project != null && editor != null);
    }

    public interface ViewUpdateListener{
        void update(ArrayList<String[]> list);
    }
}
