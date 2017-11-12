package services;

/**
 * Created by jubair on 11/12/17.
 * Time 11:42 PM
 */
public class ProjectInstance {
	public String projectPath;
	public String projectName;

	public ProjectInstance() {
		this.projectPath = null;
	}

	public void setProjectPath(String path){
		this.projectPath = path;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}
}
