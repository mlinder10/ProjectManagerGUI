package utils;

import com.example.App;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import models.Project;
import models.ProjectFACADE;
import models.Section;
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
                    App.setRoot("Project");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            projectBox.getChildren().add(title);
        }
    }

    public static VBox createDashboardProject(ProjectFACADE facade, Project project) {
        VBox container = new VBox();
        container.setStyle("-fx-alignment: center; -fx-spacing: 4; -fx-background-color: #249296; -fx-background-radius: 10; -fx-padding: 8; -fx-text-wrap: true; -fx-max-width: 120;");
        Button title = new Button(project.title);
        title.setStyle("-fx-background-color: inherit; -fx-text-fill: white;");
        title.setOnMouseClicked(event -> {
            facade.openProject(project);
            try {
                App.setRoot("Project");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        double percentage = project.getPercentage();
        ProgressBar progressBar = new ProgressBar(percentage);
        Label percentageLabel = new Label((int)(percentage * 100) + "%");
        container.getChildren().addAll(title, percentageLabel,progressBar);
        return container;
    }

    public static VBox createProjectSection(ProjectFACADE facade, Section section) {
        VBox container = new VBox();
        container.setStyle("-fx-background-color: #249296; -fx-background-radius: 10; -fx-padding: 8; -fx-min-width: 120;");
        Label title = new Label(section.title);
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18; -fx-padding: 0 0 8 0;");
        container.getChildren().add(title);
        for (Task task : section.tasks) {
            Button taskBtn = new Button(task.title);
            taskBtn.setStyle("-fx-background-color: inherit; -fx-text-fill: white; -fx-padding: 0;");
            taskBtn.setMaxWidth(100);
            taskBtn.setOnMouseClicked(event -> {
                facade.openTask(task);
                try {
                    App.setRoot("Task");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            container.getChildren().add(taskBtn);
        }
        return container;
    }
}
