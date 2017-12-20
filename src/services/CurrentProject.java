package services;

import actions.MyTypedHandler;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.graph.option.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import detection.CloneDetection;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utilites.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by jubair on 11/12/17.
 */
public class CurrentProject implements ProjectComponent {
	ProjectInstance projectInstance;
	//final static Logger logger = Logger.getLogger(CurrentProject.class);

	public CurrentProject(Project project) {
		this.projectInstance = null;
	}

	@Override
	public void initComponent() {
		// TODO: insert component initialization logic here
		//Notifications.Bus.notify(new Notification(ConsoleView.CONSOLE_CONTENT_ID, "jubair-", "init", NotificationType.INFORMATION));
	}

	@Override
	public void disposeComponent() {
		// TODO: insert component disposal logic here
		//Notifications.Bus.notify(new Notification(ConsoleView.CONSOLE_CONTENT_ID, "jubair-", "dispose", NotificationType.INFORMATION));
	}

	@Override
	@NotNull
	public String getComponentName() {
		return "CurrentProject";
	}

	@Override
	public void projectOpened() {
		// called when project is opened
		//CustomLogger.tieSystemOutAndErrToLog();
		checkDependencies();
		this.projectInstance = ServiceManager.getService(ProjectInstance.class);

		ProjectManager PM = ProjectManager.getInstance();
		Project[] AllProjects = PM.getOpenProjects();
		Project project = AllProjects[AllProjects.length - 1];

		String basePath = project.getBasePath();
		projectInstance.setProjectPath(basePath);

		String projectName = project.getName();
		projectInstance.setProjectName(projectName);

		this.projectInstance.setProjectPath(basePath + "/" + projectName);
		this.projectInstance.setProjectName(projectName);

		/*Messages.showMessageDialog(
				"Project: " + projectName +
						" is opened!", "Information", Messages.getInformationIcon());*/

		System.out.println(projectInstance.getProjectPath() + "\n" + projectInstance.getProjectName());

		CloneDetection cloneDetection = new CloneDetection(basePath);
		cloneDetection.detectClone();

		initialize();
		CodeFragments.getInstance().setFragments((CloneClasses.getCloneClasses()));
		CodeFragments.getInstance().printFragments();
	}

	private void checkDependencies() {
		if(txlOk() && nicadOk())
			return ;
		else if(!txlOk())
			setupTxl();
		else if(!nicadOk())
			setupNicad();
		else {
			setupTxl();
			setupNicad();
		}
	}

	private void setupNicad() {
		ProcessBuilder pb = new ProcessBuilder("scripts/nicad4Install.sh");
//		Map<String, String> env = pb.environment();
//		env.put("VAR1", "myValue");
//		env.remove("OTHERVAR");
//		env.put("VAR2", env.get("VAR1") + "suffix");
		pb.directory(new File("resources"));

		try {
			Process process = pb.start();
			System.out.println(pb.directory().getPath());
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = "";
			try {
				while((line = reader.readLine()) != null) {
					System.out.print(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*public static void main(String[] args) {
		CustomLogger.tieSystemOutAndErrToLog();

		CurrentProject currentProject = new CurrentProject(null);
		currentProject.setupTxl();
		currentProject.setupNicad();
	}*/
	private void setupTxl() {
		ProcessBuilder pb = new ProcessBuilder("scripts/txlInstall.sh");
//		Map<String, String> env = pb.environment();
//		env.put("VAR1", "myValue");
//		env.remove("OTHERVAR");
//		env.put("VAR2", env.get("VAR1") + "suffix");
		pb.directory(new File("resources"));

		try {
			Process p = pb.start();
//			p.getOutputStream().flush();
			System.out.println(pb.directory().getPath());
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			try {
				while((line = reader.readLine()) != null) {
					System.out.print(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean txlOk(){
		return check("/usr/local/bin/txl") || check("/usr/local/lib/txl");
	}

	private boolean nicadOk() {
		return check("/usr/local/bin/nicad4") || check("/usr/local/lib/nicad4");
	}

	private boolean check(String pathname) {
		return new File(pathname).exists();
	}

	private void initialize() {
		ClonePairs clonePairs = new ClonePairs();
		String pairFilePath = "/home/" + System.getProperty("user.name") + "/SPL3/test_projects/cloneResult/"+projectInstance.getProjectName()+"_functions-clones/"+projectInstance.getProjectName()+"_functions-clones-0.30.xml";
		clonePairs.readClonePairs(pairFilePath);
		//clonePairs.printPairs();

		CloneClasses cloneClasses = new CloneClasses();
		String classFilePath = "/home/" + System.getProperty("user.name") + "/SPL3/test_projects/cloneResult/"+projectInstance.getProjectName()+"_functions-clones/"+projectInstance.getProjectName()+"_functions-clones-0.30-classes.xml";
		cloneClasses.readCloneClasses(classFilePath);
		//cloneClasses.printClasses();
	}

	@Override
	public void projectClosed() {
		// called when project is being closed
		/*Messages.showMessageDialog(
				"Project: " + projectInstance.getProjectName() +
						" closed!", "Information", Messages.getInformationIcon());*/
	}
}
