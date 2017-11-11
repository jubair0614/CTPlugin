package org.jubair.tracker;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.List;

/**
 * Created by jubair on 11/10/17.
 */
public class GenerateAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent e) {
		PsiClass psiClass = getPsiClassFromContext(e);
		GenerateDialog dialog = new GenerateDialog(psiClass);
		dialog.show();
		if( dialog.isOK() ){
			generateComparable(psiClass, dialog.getFiled());
		}
	}

	private void generateComparable(final PsiClass psiClass, final List<PsiField> fileds) {
		new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {

			public void run() throws Throwable {
				generateCompareTo(psiClass, fileds);
				generateImplementsComparable(psiClass);
			}
		}.execute();
	}

	private void generateImplementsComparable(PsiClass psiClass) {
		PsiClassType[] implementsListTypes = psiClass.getImplementsListTypes();
		for (PsiClassType implementsListType:
			 implementsListTypes) {
			PsiClass resolved = implementsListType.resolve();
			if(resolved != null && "java.lang.Comparable".equals(resolved.getQualifiedName())){
				return;
			}

			String implementsType = "Comparable<" + psiClass.getName() + ">";
			PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
			PsiJavaCodeReferenceElement referenceElement = elementFactory.createReferenceFromText(implementsType, psiClass);
			psiClass.getImplementsList().add(referenceElement);
		}
	}

	public void generateCompareTo(PsiClass psiClass, List<PsiField> fileds) {
		StringBuilder builder = new StringBuilder("public int compareTo(");
		builder.append(psiClass.getName()).append(" that) {\n");
		builder.append("return com.google.common.collect.ComparisonChain.start()");

		for (PsiField psiField: fileds){
			builder.append(".compare(this.").append(psiField.getName()).append(", that.");
			builder.append(psiField.getName()).append(")");
		}
		builder.append(".result();\n}");
		PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
		PsiMethod compareTo = elementFactory.createMethodFromText(builder.toString(), psiClass);
		PsiElement method = psiClass.add(compareTo);
		JavaCodeStyleManager.getInstance(psiClass.getProject()).shortenClassReferences(method);
	}


	@Override
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
