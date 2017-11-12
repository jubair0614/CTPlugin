import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by jubair on 10/16/17.
 */
public class CloneTracking extends AnAction {
	@Override
	public void actionPerformed(AnActionEvent anActionEvent) {
		System.out.println("This is clone tracking");
	}

	@Override
	public void update(AnActionEvent e) {
		super.update(e);
	}
}
