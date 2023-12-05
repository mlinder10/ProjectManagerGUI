package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import com.example.App;
import models.*;

import javafx.fxml.Initializable;

public class RegisterController implements Initializable {

    @FXML
    private ImageView backgroundImage;

    @FXML
    private Label titleLabel;

    @FXML
    private Label becomeTaskSharer;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private void loginBtnClick(MouseEvent event) {
        try {
            App.setRoot("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void registerBtnClick(MouseEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        

        if (UserList.getUserList().register(email, username, password)) {
            try {
                App.setRoot("Dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loginBtn.setOnMouseClicked(event -> loginBtnClick(event));
        registerBtn.setOnMouseClicked(event -> registerBtnClick(event));
    }

}
