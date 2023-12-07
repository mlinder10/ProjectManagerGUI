package controllers;

import java.io.IOException;
import java.net.URL;
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
    private Button createBtn;

    @FXML
    private HBox sectionBox;

    @FXML
    private Button dashboardBtn;

    @FXML
    private VBox sidenavProjects;

    @FXML
    private VBox sidenavTasks;

    private void handleCreateBtnClick() {
        createBtn.setOnMouseClicked(event -> {
            VBox modal = new VBox();
            modal.setAlignment(Pos.CENTER);
            modal.setStyle("-fx-background-color: #000a;");
            VBox modalInernal = new VBox();
            modalInernal.setStyle("-fx-alignment: center; -fx-spacing: 8; -fx-background-color: #249296aa; -fx-background-radius: 30; -fx-padding: 8;");
            modalInernal.setMaxWidth(260);
            modalInernal.setPrefWidth(300);
            modalInernal.setPrefHeight(200);
            Label title = new Label("Create Section");
            title.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18;");
            TextField input = new TextField();
            input.setPromptText("Section Title");
            input.setMaxWidth(200);

            HBox buttons = new HBox();
            buttons.setStyle("-fx-spacing: 8; -fx-alignment: center;");
            Button cancel = new Button("Cancel");
            cancel.setOnMouseClicked(event2 -> {
                stack.getChildren().remove(modal);
            });
            Button create = new Button("Create");
            create.setOnMouseClicked(event3 -> {
                facade.createSection(input.getText());
                SceneBuilder.populateSections(facade, project.sections, stack, sectionBox);
                SceneBuilder.populateNavbar(facade, modal, modalInernal);
                stack.getChildren().remove(modal);
            });

            buttons.getChildren().addAll(cancel, create);
            modalInernal.getChildren().addAll(title, input, buttons);
            modal.getChildren().add(modalInernal);
            stack.getChildren().add(modal);
        });
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
        SceneBuilder.populateSections(facade, project.sections, stack, sectionBox);
        title.setText(project.title);
        commentsBtn.setText("Comments (" + project.getCommentsSize() + ")");
        commentsBtn.setOnMouseClicked(event -> {
            try {
                App.setRoot("Comments");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        handleCreateBtnClick();
    }

}
