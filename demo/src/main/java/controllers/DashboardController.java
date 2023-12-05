package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Project;
import models.ProjectFACADE;
import models.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class DashboardController implements Initializable {
    private ProjectFACADE facade;

    @FXML
    private HBox ownerHbox;

    @FXML
    private HBox memberHbox;

    @FXML
    private VBox sidenavTasks;

    @FXML
    private VBox sidenavProjects;

    private void populateSidenav() {
        ArrayList<Task> tasks = facade.getUserTasks();
        ArrayList<Project> projects = facade.getUserProjects();
        for (Task task : tasks) {
            Button title = new Button(task.title);
            sidenavTasks.getChildren().add(title);
        }
        for (Project project : projects) {
            Button title = new Button(project.title);
            sidenavProjects.getChildren().add(title);
        }
    }

    private void createProjects() {
        ArrayList<Project> owned = facade.getOwnerProjects();
        ArrayList<Project> member = facade.getMemberProjects();
        for (Project project : owned) {
            VBox projectContainer = new VBox();
            Button title = new Button(project.title);
            ProgressBar progressBar = new ProgressBar(project.getPercentage());
            projectContainer.getChildren().addAll(title, progressBar);
            ownerHbox.getChildren().add(projectContainer);
        }
        for (Project project : member) {
            VBox projectContainer = new VBox();
            Button title = new Button(project.title);
            ProgressBar progressBar = new ProgressBar(project.getPercentage());
            projectContainer.getChildren().addAll(title, progressBar);
            memberHbox.getChildren().add(projectContainer);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facade = ProjectFACADE.getInstance();
        populateSidenav();
        createProjects();
    }

}
