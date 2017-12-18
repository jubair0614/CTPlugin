package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import detection.CloneDetection;
import services.ProjectInstance;
import utilites.CloneClasses;
import utilites.CloneMapper;
import utilites.ClonePairs;

/**
 * Created by jubair on 9/22/17.
 */
public class CloneDetectionAction extends AnAction {

	public CloneDetectionAction() {
	}

	@Override
	public void actionPerformed(AnActionEvent anActionEvent) {
		String projectName = anActionEvent.getProject().getName();

		String path = anActionEvent.getProject().getBaseDir().toString();
		path = path.substring(7, path.length());

		CloneDetection cloneDetection = new CloneDetection(path);
		cloneDetection.detectClone();

		initializeClones(projectName);
	}

	private void initializeClones(String projectName) {
		ClonePairs clonePairs = new ClonePairs();
		//String userName = System.getProperty("user.name");
		//String pairFilePath = "/home/" + userName+"/tool2/cloneResult/"+projectName+"_functions-clones/"+projectName+"_functions-clones-0.30.xml";
		String pairFilePath = "/home/jubair/SPL3/test_projects/cloneResult/"+projectName+"_functions-clones/"+projectName+"_functions-clones-0.30.xml";
		clonePairs.readClonePairs(pairFilePath);
		clonePairs.printPairs();

		CloneClasses cloneClasses = new CloneClasses();
		String classFilePath = "/home/jubair/SPL3/test_projects/cloneResult/"+projectName+"_functions-clones/"+projectName+"_functions-clones-0.30-classes.xml";
		cloneClasses.readCloneClasses(classFilePath);
		cloneClasses.printClasses();

//		CloneMapper.getInstance().setMapper(cloneClasses);
//		CloneMapper.getInstance().printMap();
	}

}
