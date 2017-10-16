import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
/**
 * Created by jubair on 10/15/17.
 */
public class EditorIllustration extends AnAction {
	static {
		final EditorActionManager actionManager = EditorActionManager.getInstance();
		final TypedAction typedAction = actionManager.getTypedAction();
		typedAction.setupHandler(new TypedHandler());
	}

	@Override
	public void actionPerformed(AnActionEvent anActionEvent) {
		System.out.println(anActionEvent.getDataContext());
	}
}
