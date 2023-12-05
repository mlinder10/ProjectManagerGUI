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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class DashboardController implements Initializable {
    private ProjectFACADE facade;

    @FXML 
    private HBox ownerHbox;

    @FXML
    private HBox memberHbox;

    private void createProjects() {
        ArrayList<Project> projects = facade.getAllProjects();
        for (Project project : projects) {
            VBox projectContainer = new VBox();
            Button title = new Button(project.title);
            ProgressBar progressBar = new ProgressBar(project.getPercentage());
            projectContainer.getChildren().addAll(title, progressBar);
            if (project.owner.id.equals(facade.getUser().id)) {
                ownerHbox.getChildren().add(projectContainer);
            } else {
                memberHbox.getChildren().add(projectContainer);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facade = ProjectFACADE.getInstance();
        createProjects();
    }

}
