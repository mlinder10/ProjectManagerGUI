package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import models.ProjectFACADE;
import utils.SceneBuilder;

public class ProjectController implements Initializable {
    private ProjectFACADE facade;

    @FXML
    private Button dashboardBtn;

    @FXML
    private VBox sidenavProjects;

    @FXML
    private VBox sidenavTasks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facade = ProjectFACADE.getInstance();
        SceneBuilder.populateNavbar(facade, sidenavProjects, sidenavTasks);
        dashboardBtn.setOnMouseClicked(event -> {
            try {
                App.setRoot("Dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
