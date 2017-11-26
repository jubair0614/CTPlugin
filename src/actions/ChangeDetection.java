package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import tracker.FileGenerator;
import tracker.JavaDiffUtils;

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
