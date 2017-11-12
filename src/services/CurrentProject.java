package services;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * Created by jubair on 11/12/17.
 */
public class CurrentProject implements ProjectComponent {
	ProjectInstance projectInstance;

	public CurrentProject(Project project) {
		this.projectInstance = null;
	}

	@Override
	public void initComponent() {
		// TODO: insert component initialization logic here
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
		Messages.showMessageDialog(
				"Project: " + projectName +
						" is opened!", "Information", Messages.getInformationIcon());

		System.out.println(projectInstance.getProjectPath() + "\n" + projectInstance.getProjectName());
	}

	@Override
	public void projectClosed() {
		// called when project is being closed
		Messages.showMessageDialog(
				"Project: " + projectInstance.getProjectName() +
						" closed!", "Information", Messages.getInformationIcon());
	}
}
