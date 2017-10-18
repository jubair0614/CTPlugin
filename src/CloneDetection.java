import com.intellij.openapi.actionSystem.AnActionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jubair on 9/22/17.
 */
public class CloneDetection extends com.intellij.openapi.actionSystem.AnAction {

	private String projectPath;

	public CloneDetection() {
		this.projectPath = null;
	}

	@Override
	public void actionPerformed(AnActionEvent anActionEvent) {
		System.out.println("Project Name: " + anActionEvent.getProject().getName());

		this.projectPath = anActionEvent.getProject().getBaseDir().toString();
		this.projectPath = this.projectPath.substring(7, projectPath.length());
		System.out.println(this.projectPath);

		String sourcercc = "/home/jubair/IdeaProjects/Plugin/resources/scripts/runSourcerCCWithProject.sh " + this.projectPath;
		String nicad4 = "/home/jubair/IdeaProjects/Plugin/resources/scripts/runNicad4.sh " + this.projectPath;
		detectClone(nicad4);

	}

	private void detectClone(String script) {
		Process proc = null;
		try {
			proc = Runtime.getRuntime().exec(script);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Read the output

		BufferedReader reader =
				new BufferedReader(new InputStreamReader(proc.getInputStream()));

		String line = "";
		try {
			while((line = reader.readLine()) != null) {
				System.out.print(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String  getProjectPath(){
		System.out.println("Project Path: " + this.projectPath);
		return this.projectPath;
	}

	/*public static void main(String[] args) {

	}*/
}
