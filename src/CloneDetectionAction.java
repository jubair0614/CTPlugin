import com.intellij.openapi.actionSystem.AnActionEvent;
import detection.CloneDetection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jubair on 9/22/17.
 */
public class CloneDetectionAction extends com.intellij.openapi.actionSystem.AnAction {

	public CloneDetectionAction() {
	}

	@Override
	public void actionPerformed(AnActionEvent anActionEvent) {
		String project = anActionEvent.getProject().getName();

		String path = anActionEvent.getProject().getBaseDir().toString();
		path = path.substring(7, path.length());

		CloneDetection cloneDetection = new CloneDetection(path);
		cloneDetection.detectClone();
	}

}
