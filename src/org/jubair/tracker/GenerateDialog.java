package org.jubair.tracker;

import com.intellij.ide.util.PsiClassListCellRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ToolbarDecorator;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by jubair on 11/10/17.
 */
public class GenerateDialog extends DialogWrapper{
	private CollectionListModel<PsiField> myFields;
	private final LabeledComponent<JPanel> myComponent;

	public GenerateDialog(PsiClass psiClass) {
		super(psiClass.getProject());
		setTitle("Select fields for comparison chain");

		myFields = new CollectionListModel<>(psiClass.getAllFields());
		JList fieldList = new JList(myFields);
		fieldList.setCellRenderer(new DefaultListCellRenderer());
		ToolbarDecorator decorator = ToolbarDecorator.createDecorator(fieldList);
		decorator.disableAddAction();
		JPanel panel = decorator.createPanel();
		myComponent = LabeledComponent.create(panel, "Fileds to include in compareTo()");

		init();
	}

	@Nullable
	@Override
	protected JComponent createCenterPanel() {
		return myComponent;
	}

	public List<PsiField> getFiled() {
		return myFields.getItems();
	}
}
