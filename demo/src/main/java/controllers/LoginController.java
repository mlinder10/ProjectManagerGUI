package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.ProjectFACADE;
import javafx.fxml.Initializable;

public class LoginController implements Initializable {
    private ProjectFACADE facade;

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
    private void handleLoginButtonClick(MouseEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (facade.login(username, password)) {
            try {
                App.setRoot("Dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error");
        }
    }

    @FXML
    private void handleRegisterButtonClick(MouseEvent event) {
        try {
            App.setRoot("Register");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        facade = ProjectFACADE.getInstance();
        registerBtn.setOnMouseClicked(event -> handleRegisterButtonClick(event));
        loginBtn.setOnMouseClicked(event -> handleLoginButtonClick(event));
    }
}
