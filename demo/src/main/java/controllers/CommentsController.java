package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Project;
import models.ProjectFACADE;
import models.Task;
import models.Comment;
import utils.SceneBuilder;

public class CommentsController implements Initializable {
    private ProjectFACADE facade;
    private Project project;
    private Task task;

    @FXML
    private StackPane stack;

    @FXML
    private Button dashboardBtn;

    @FXML
    private VBox sidenavTasks;

    @FXML
    private VBox sidenavProjects;

    @FXML
    private Label title;

    @FXML
    private VBox commentsBox;

    @FXML
    private Button createBtn;

    private void populateComments() {
        commentsBox.getChildren().clear();
        ArrayList<Comment> comments = project == null ? task.comments : project.comments;
        title.setText(project == null ? task.title + " (Task)" : project.title + " (Project)");
        title.setOnMouseClicked(event -> {
            try {
                if (project == null) {
                    App.setRoot("Task");
                } else {
                    App.setRoot("Project");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        for (Comment comment : comments) {
            VBox commentContainer = SceneBuilder.createComment(comment, stack, facade);
            commentsBox.getChildren().add(commentContainer);
        }
    }

    private void handleCreateBtnClick() {
        createBtn.setOnMouseClicked(event -> {
            VBox modal = new VBox();
            modal.setAlignment(Pos.CENTER);
            modal.setStyle("-fx-background-color: #000a;");
            VBox modalInternal = new VBox();
            modalInternal.setStyle(
                    "-fx-alignment: center; -fx-spacing: 8; -fx-background-color: #249296aa; -fx-background-radius: 30; -fx-padding: 8;");
            modalInternal.setMaxWidth(260);
            modalInternal.setPrefWidth(300);
            modalInternal.setPrefHeight(200);
            Label inputTitle = new Label("Create Comment");
            inputTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField contentInput = new TextField();
            contentInput.setPromptText("Comment Content");
            contentInput.setMaxWidth(200);
            HBox buttons = new HBox();
            buttons.setStyle("-fx-spacing: 8; -fx-alignment: center;");
            Button cancel = new Button("Cancel");
            cancel.setOnMouseClicked(event2 -> {
                stack.getChildren().remove(modal);
            });
            Button create = new Button("Create");
            create.setOnMouseClicked(event2 -> {
                if (project == null)
                    facade.createComment(task, contentInput.getText());
                else
                    facade.createComment(project, contentInput.getText());

                populateComments();
                stack.getChildren().remove(modal);
            });
            buttons.getChildren().addAll(cancel, create);
            modalInternal.getChildren().addAll(inputTitle, contentInput, buttons);
            modal.getChildren().add(modalInternal);
            stack.getChildren().add(modal);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facade = ProjectFACADE.getInstance();
        project = facade.getCurrentProject();
        task = facade.getCurrentTask();
        SceneBuilder.populateNavbar(facade, sidenavProjects, sidenavTasks);
        dashboardBtn.setOnMouseClicked(event -> {
            try {
                App.setRoot("Dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        populateComments();
        handleCreateBtnClick();
    }
}
