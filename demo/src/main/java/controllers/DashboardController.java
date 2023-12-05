package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.App;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import models.ProjectFACADE;
import javafx.fxml.Initializable;

public class DashboardController implements Initializable {
    private ProjectFACADE facade;

    private Button Project1;
    private Button Project2;
    private Button Project3;
    private Button Task1;
    private Button Task2;

    private void handleProject1ButtonClick(MouseEvent event) {

        ProjectFACADE facade = ProjectFACADE.getInstance();

        facade.getProject("Project 1");
        facade.openProject("Project 1");
        try {
            App.setRoot("OwnerProject");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleTask1ButtonClick(MouseEvent event) {
        ProjectFACADE facade = ProjectFACADE.getInstance();

        facade.getTask("Task 1");
        try {
            App.setRoot("task1");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facade = ProjectFACADE.getInstance();
        Project1.setOnMouseClicked(event -> handleProject1ButtonClick(event));
        Task1.setOnMouseClicked(event -> handleTask1ButtonClick(event));
    }

}
