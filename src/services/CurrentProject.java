package services;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import detection.CloneDetection;
import org.jetbrains.annotations.NotNull;
import utilites.CloneClasses;
import utilites.ClonePairs;

/**
 * Created by jubair on 11/12/17.
 */
public class CurrentProject implements ProjectComponent {
	ProjectInstance projectInstance;
	String projectName;
	String projectPath;

	public CurrentProject(Project project) {
		this.projectInstance = null;
	}

	@Override
	public void initComponent() {
		// TODO: insert component initialization logic here

		ClonePairs clonePairs = new ClonePairs();
		String pairFilePath = "/home/jubair/SPL3/test_projects/cloneResult/"+projectName+"_functions-clones/"+projectName+"_functions-clones-0.30.xml";
		clonePairs.readClonePairs(pairFilePath);

		CloneClasses cloneClasses = new CloneClasses();
		String classFilePath = "/home/jubair/SPL3/test_projects/cloneResult/"+projectName+"_functions-clones/"+projectName+"_functions-clones-0.30-classes.xml";
		cloneClasses.readCloneClasses(classFilePath);
	}

	@Override
	public void disposeComponent() {
		// TODO: insert component disposal logic here
	}

	@Override
	@NotNull
	public String getComponentName() {
		return "CurrentProject";
	}

	@Override
	public void projectOpened() {
		// called when project is opened
		this.projectInstance = ServiceManager.getService(ProjectInstance.class);

		ProjectManager PM = ProjectManager.getInstance();
		Project[] AllProjects = PM.getOpenProjects();
		Project project = AllProjects[AllProjects.length - 1];

		String basePath = project.getBasePath();
		projectInstance.setProjectPath(basePath);

		String projectName = project.getName();
		projectInstance.setProjectName(projectName);

		this.projectPath = basePath + "/" + projectName;
		this.projectName = projectName;

		Messages.showMessageDialog(
				"Project: " + projectName +
						" is opened!", "Information", Messages.getInformationIcon());

		System.out.println(projectInstance.getProjectPath() + "\n" + projectInstance.getProjectName());

		CloneDetection cloneDetection = new CloneDetection(basePath);
		cloneDetection.detectClone();
	}

	@Override
	public void projectClosed() {
		// called when project is being closed
		Messages.showMessageDialog(
				"Project: " + projectInstance.getProjectName() +
						" closed!", "Information", Messages.getInformationIcon());
	}
}
