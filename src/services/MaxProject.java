package services;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;

public class MaxProject implements ProjectComponent {
    public MaxProject(Project project) {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
        Notifications.Bus.notify(new Notification(ConsoleView.CONSOLE_CONTENT_ID, "jubair-", "init", NotificationType.INFORMATION));
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
        Notifications.Bus.notify(new Notification(ConsoleView.CONSOLE_CONTENT_ID, "jubair-", "dispose", NotificationType.INFORMATION));
    }

    @Override
    public String getComponentName() {
        return "MaxProject";
    }

    public void projectOpened() {
        // called when project is opened
        ProjectCounter CommandCounter = ServiceManager.getService(ProjectCounter.class);

        if (CommandCounter.IncreaseCounter() == -1) {
            Messages.showMessageDialog(
                    "The maximum number of opened projects exceeds " + String.valueOf(CommandCounter.MaxCount) +
                            " projects!", "Error", Messages.getErrorIcon());
            ProjectManager PM = ProjectManager.getInstance();
            Project[] AllProjects = PM.getOpenProjects();
            Project project = AllProjects[AllProjects.length - 1];
            PM.closeProject(project);
        }
    }


    public void projectClosed() {
        // called when project is being closed
        ProjectCounter CommandCounter = ServiceManager.getService(ProjectCounter.class);
        CommandCounter.DecreaseCounter();
    }
}
