package controllers;

import java.io.IOException;

import com.example.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import models.ProjectFACADE;

public class LoginController {

    @FXML
    private ImageView backgroundImage;

    @FXML
    private Label titleLabel;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private void initialize() {
        // You can perform additional initialization here if needed
    }

    @FXML
    private void handleLoginButtonClick() {
        // Handle login button click event
        String username = usernameField.getText();
        String password = passwordField.getText();
        loginBtn.setOnMouseClicked(event -> {
            ProjectFACADE facade = ProjectFACADE.getInstance();
            if (facade.login(username, password)) {
                try {
                    App.setRoot("Dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("error");
            }
        });
    }

    @FXML
    private void handleRegisterButtonClick() {
        registerBtn.setOnMouseClicked(event -> {
            try {
                App.setRoot("Register");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
