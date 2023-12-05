package utils;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import models.Project;
import models.ProjectFACADE;
import models.Task;

public class SceneBuilder {
    public static void populateNavbar(ProjectFACADE facade, VBox projectBox, VBox taskBox) {
        ArrayList<Task> tasks = facade.getUserTasks();
        ArrayList<Project> projects = facade.getUserProjects();
        for (Task task : tasks) {
            Button title = new Button(task.title);
            taskBox.getChildren().add(title);
        }
        for (Project project : projects) {
            Button title = new Button(project.title);
            projectBox.getChildren().add(title);
        }
    }
}
