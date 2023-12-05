package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Project;
import models.ProjectFACADE;
import models.Section;
import utils.SceneBuilder;

public class ProjectController implements Initializable {
    private ProjectFACADE facade;
    private Project project;

    @FXML
    private StackPane stack;

    @FXML
    private Label title;

    @FXML
    private Button commentsBtn;

    @FXML
    private HBox sectionBox;

    @FXML
    private Button dashboardBtn;

    @FXML
    private VBox sidenavProjects;

    @FXML
    private VBox sidenavTasks;

    private void populateSections() {
        for (Section section : project.sections) {
            VBox sectionContainer = SceneBuilder.createProjectSection(facade, section);
            sectionBox.getChildren().add(sectionContainer);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facade = ProjectFACADE.getInstance();
        project = facade.getCurrentProject();
        SceneBuilder.populateNavbar(facade, sidenavProjects, sidenavTasks);
        dashboardBtn.setOnMouseClicked(event -> {
            try {
                App.setRoot("Dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        populateSections();
        title.setText(project.title);
        commentsBtn.setText("Comments (" + project.getCommentsSize() + ")");
    }

}
