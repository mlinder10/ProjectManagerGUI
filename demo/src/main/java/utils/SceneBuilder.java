package utils;

import com.example.App;

import java.io.IOException;
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
            title.setStyle("-fx-background-color: inherit; -fx-text-fill: white;");
            title.setOnMouseClicked(event -> {
                facade.openTask(task);
                try {
                    App.setRoot("Task");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            taskBox.getChildren().add(title);
        }
        for (Project project : projects) {
            Button title = new Button(project.title);
            title.setStyle("-fx-background-color: inherit; -fx-text-fill: white;");
            title.setOnMouseClicked(event -> {
                facade.openProject(project);
                try {
                    App.setRoot("OwnerProject");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            projectBox.getChildren().add(title);
        }
    }
}
