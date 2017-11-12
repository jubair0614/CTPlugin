package detection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jubair on 9/22/17.
 */
public class CloneDetection{

	private String projectPath;

	public CloneDetection(String projectPath) {
		this.projectPath = projectPath;
	}

	private String toolChooser(){
		//String sourcercc = "/home/jubair/IdeaProjects/Plugin/resources/scripts/runSourcerCCWithProject.sh " + this.projectPath;

		String nicad4 = "/home/jubair/IdeaProjects/CTPlugin/resources/scripts/runNicad4.sh " + this.projectPath;

		return nicad4;
	}

	public void detectClone() {
		String toolScript = toolChooser();
		Process proc = null;
		try {
			proc = Runtime.getRuntime().exec(toolScript);
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
		return this.projectPath;
	}

	public void setProjectPath(String projectPath){
		this.projectPath = projectPath;
	}

}
