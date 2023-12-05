package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.ProjectFACADE;
import models.Task;
import models.User;
import utils.SceneBuilder;

public class TaskController implements Initializable {
    private ProjectFACADE facade;
    private Task task;

    @FXML
    VBox sidenavTasks;

    @FXML
    VBox sidenavProjects;

    @FXML
    Label title;

    @FXML
    Text description;

    @FXML
    VBox userList;

    private void populateTask() {
        title.setText(task.title);
        description.setText(task.description);
    }

    private void populateAssignedUsers() {
        for (User user : task.assignedUsers) {
            VBox userContainer = new VBox();
            Label username = new Label(user.username);
            Label email = new Label(user.email);
            userContainer.getChildren().addAll(username, email);
            userList.getChildren().add(userContainer);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facade = ProjectFACADE.getInstance();
        task = facade.getCurrentTask();
        SceneBuilder.populateNavbar(facade, sidenavProjects, sidenavTasks);
        populateTask();
        populateAssignedUsers();
    }

}
