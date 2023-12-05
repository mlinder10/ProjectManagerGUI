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
        for (int i=0; i<ownerHbox.getChildren().size(); i++) {
            ownerHbox.getChildren().remove(i);
        }
        for (int i=0; i<memberHbox.getChildren().size(); i++) {
            memberHbox.getChildren().remove(i);
        }
        for (Project project : owned) {
            VBox projectContainer = new VBox();
            Button title = new Button(project.title);
            title.setOnMouseClicked(event -> {
                facade.openProject(project);
                try {
                    App.setRoot("OwnerProject");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ProgressBar progressBar = new ProgressBar(project.getPercentage());
            projectContainer.getChildren().addAll(title, progressBar);
            ownerHbox.getChildren().add(projectContainer);
        }
        for (Project project : member) {
            VBox projectContainer = new VBox();
            Button title = new Button(project.title);
            title.setOnMouseClicked(event -> {
                facade.openProject(project);
                try {
                    App.setRoot("MemberProject");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ProgressBar progressBar = new ProgressBar(project.getPercentage());
            projectContainer.getChildren().addAll(title, progressBar);
            memberHbox.getChildren().add(projectContainer);
        }
    }

    private void handleCreateBtnClick() {
        createBtn.setOnMouseClicked(event -> {
            System.out.println("Button clicked");
            VBox modal = new VBox();
            VBox modalInternal = new VBox();
            Button cancel = new Button("Cancel");
            cancel.setOnMouseClicked(event2 -> {
                stack.getChildren().remove(modal);
            });
            Label title = new Label("Create Project");
            TextField input = new TextField("Project title");
            Button create = new Button("Create");
            create.setOnMouseClicked(event3 -> {
                facade.createProject(input.getText());
                populateProjects();
                stack.getChildren().remove(modal);
            });
            modalInternal.getChildren().addAll(cancel, title, input, create);
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
