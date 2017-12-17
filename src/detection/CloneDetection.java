package detection;

import org.apache.log4j.Logger;
import services.CurrentProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jubair on 9/22/17.
 */
public class CloneDetection{

	private String projectPath;
	final static Logger logger = Logger.getLogger(CloneDetection.class);

	public CloneDetection(String projectPath) {
		this.projectPath = projectPath;
	}

	private String toolChooser(int num){
		String tool;
		switch (num){
			case 1:
				System.out.println("nicad-4.0");
				tool = "/home/jubair/IdeaProjects/CTPlugin/resources/scripts/runNicad4.sh " + this.projectPath;
				break;
			case 2:
				System.out.println("SourcererCC");
				tool = "/home/jubair/IdeaProjects/CTPlugin/resources/scripts/runSourcerCCWithProject.sh " + this.projectPath;
				break;
			default:
				System.out.println("nicad-4.0");
				tool = "/home/jubair/IdeaProjects/CTPlugin/resources/scripts/runNicad4.sh " + this.projectPath;
		}
		return tool;
	}

	public void detectClone() {
		String toolScript = toolChooser(1);
		System.out.println("Project: " + getProjectPath());
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
				logger.debug(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
