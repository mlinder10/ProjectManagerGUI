package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Project;
import models.ProjectFACADE;
import utils.SceneBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

public class DashboardController implements Initializable {
    private ProjectFACADE facade;

    @FXML
    private StackPane stack;

    @FXML
    private Button createBtn;

    @FXML
    private HBox ownerHbox;

    @FXML
    private HBox memberHbox;

    @FXML
    private VBox sidenavTasks;

    @FXML
    private VBox sidenavProjects;

    private void populateProjects() {
        ArrayList<Project> owned = facade.getOwnerProjects();
        ArrayList<Project> member = facade.getMemberProjects();
        ownerHbox.getChildren().clear();
        memberHbox.getChildren().clear();
        for (Project project : owned) {
            VBox projectContainer = SceneBuilder.createDashboardProject(facade, project);
            ownerHbox.getChildren().add(projectContainer);
        }
        for (Project project : member) {
            VBox projectContainer = SceneBuilder.createDashboardProject(facade, project);
            memberHbox.getChildren().add(projectContainer);
        }
    }

    private void handleCreateBtnClick() {
        createBtn.setOnMouseClicked(event -> {
            VBox modal = new VBox();
            modal.setAlignment(Pos.CENTER);
            modal.setStyle("-fx-background-color: #000a;");
            VBox modalInternal = new VBox();
            modalInternal.setStyle("-fx-alignment: center; -fx-spacing: 8; -fx-background-color: #249296aa; -fx-background-radius: 30; -fx-padding: 8;");
            modalInternal.setMaxWidth(260);
            modalInternal.setPrefWidth(300);
            modalInternal.setPrefHeight(200);
            Label title = new Label("Create Project");
            title.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18;");
            TextField input = new TextField();
            input.setPromptText("Project Title");
            input.setMaxWidth(200);

            HBox buttons = new HBox();
            buttons.setStyle("-fx-spacing: 8; -fx-alignment: center;");
            Button cancel = new Button("Cancel");
            cancel.setOnMouseClicked(event2 -> {
                stack.getChildren().remove(modal);
            });
            Button create = new Button("Create");
            create.setOnMouseClicked(event3 -> {
                facade.createProject(input.getText());
                populateProjects();
                SceneBuilder.populateNavbar(facade, modal, modalInternal);
                stack.getChildren().remove(modal);
            });

            buttons.getChildren().addAll(cancel, create);
            modalInternal.getChildren().addAll(title, input, buttons);
            modal.getChildren().add(modalInternal);
            stack.getChildren().add(modal);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facade = ProjectFACADE.getInstance();
        SceneBuilder.populateNavbar(facade, sidenavProjects, sidenavTasks);
        populateProjects();
        handleCreateBtnClick();
    }
}
