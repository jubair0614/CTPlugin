package actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import tracker.FileGenerator;
import utilites.*;

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
        //typedAction.setupHandler(new MyTypedHandler());
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

        fragmentPath = psiFile.getContainingDirectory().toString() + "/" + psiFile.getName();
        System.out.println(fragmentPath);
        fragmentPath = fragmentPath.substring(13, fragmentPath.length());

        if(elementAt.getFirstChild().getNode().getElementType().toString().equals("DOC_COMMENT")) {
            int[] positions = removeCommentDoc(editor, elementAt);
            this.startLine = positions[0];
            this.endLine = positions[1];
        }

            /*PSIContainer.getInstance().setOriginal(elementAt);
            PSIContainer.getInstance().analyze(PSIContainer.getInstance().getOriginal());*/

        else {
            TextRange textRange = elementAt.getTextRange();
            VisualPosition startLinePosition = editor.offsetToVisualPosition(textRange.getStartOffset());
            VisualPosition endLinePosition = editor.offsetToVisualPosition(textRange.getEndOffset());

            this.startLine = startLinePosition.line + 1;
            this.endLine = endLinePosition.line + 1;
            System.out.println("startLine: " + this.startLine + " " + "endLine: " + this.endLine);
        }
        CloneClass cloneClass = CloneClasses.getCloneClass(fragmentPath, startLine, endLine);
        updateToolWindow(cloneClass);
        //CloneClass changeClass = CloneClasses.getCloneClass(fragmentPath, startLine);

        startEditorListener(editor, fragment, fragmentPath);
    }

    private void startEditorListener(Editor editor, String fragment, String fragmentPath) {
        editor.getCaretModel().addCaretListener(new CaretListener() {
            @Override
            public void caretPositionChanged(CaretEvent caretEvent) {
                System.out.println("Caret listener: " + caretEvent.getCaret().getVisualLineStart());
                int position = caretEvent.getNewPosition().toVisualPosition().line + 1;
                System.out.println(position);
                System.out.println(caretEvent.getSource().getClass().getName());
                checkForClone(fragmentPath, position);
            }

            private void checkForClone(String fragmentPath, int position) {
                CloneFragment cloneFragment = CloneClasses.validClone(fragmentPath, position);
                if(cloneFragment != null) {
                    CloneClass cloneClass = CloneClasses.getCloneClass(fragmentPath, position);
                    CodeFragment fragmentWithContent = CodeFragments.getInstance().getFragmentWithContent(cloneFragment);
                    if(fragmentWithContent != null){
                        FileGenerator.getInstance().setOriginal(fragmentWithContent.getContent());
                    }
                    CurrentClassContainer.getInstance().setCloneClass(cloneClass);
                }
            }

            @Override
            public void caretAdded(CaretEvent caretEvent) {

            }

            @Override
            public void caretRemoved(CaretEvent caretEvent) {

            }
        });
    }

    private int[] removeCommentDoc(Editor editor, PsiElement fragment) {
        PsiElement firstChild = fragment.getFirstChild();
        System.out.println(firstChild.getNode().getElementType().toString());

        int endOffset1 = firstChild.getTextRange().getEndOffset();
        VisualPosition visualPosition = editor.offsetToVisualPosition(endOffset1);
        System.out.println(visualPosition.line);
        int startLine = visualPosition.line + 2;
        int endOffset = fragment.getTextRange().getEndOffset();
        VisualPosition visualPosition1 = editor.offsetToVisualPosition(endOffset);
        int endLine = visualPosition1.line+1;
        System.out.println(startLine + " " + endLine);
        int[] positions = {startLine, endLine};
        return positions;
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
        else{
            System.out.println("Not found the specified clone");

        }

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
