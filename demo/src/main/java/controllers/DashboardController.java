package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.App;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
        for (int i = 0; i < ownerHbox.getChildren().size(); i++) {
            ownerHbox.getChildren().remove(i);
        }
        for (int i = 0; i < memberHbox.getChildren().size(); i++) {
            memberHbox.getChildren().remove(i);
        }
        for (Project project : owned) {
            VBox projectContainer = createProject(project);
            ownerHbox.getChildren().add(projectContainer);
        }
        for (Project project : member) {
            VBox projectContainer = createProject(project);
            memberHbox.getChildren().add(projectContainer);
        }
    }

    private void handleCreateBtnClick() {
        createBtn.setOnMouseClicked(event -> {
            VBox modal = new VBox();
            modal.setAlignment(Pos.CENTER);
            modal.setStyle("-fx-background-color: #000a;;");
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
                stack.getChildren().remove(modal);
            });

            buttons.getChildren().addAll(cancel, create);
            modalInternal.getChildren().addAll(title, input, buttons);
            modal.getChildren().add(modalInternal);
            stack.getChildren().add(modal);
        });
    }

    private VBox createProject(Project project) {
        VBox projectContainer = new VBox();
        projectContainer.setStyle("-fx-alignment: center; -fx-spacing: 4;");
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
        double percentage = project.getPercentage();
        ProgressBar progressBar = new ProgressBar(percentage);
        Label percentageLabel = new Label((int)(percentage * 100) + "%");
        projectContainer.getChildren().addAll(title, percentageLabel,progressBar);
        return projectContainer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facade = ProjectFACADE.getInstance();
        SceneBuilder.populateNavbar(facade, sidenavProjects, sidenavTasks);
        populateProjects();
        handleCreateBtnClick();
    }
}
