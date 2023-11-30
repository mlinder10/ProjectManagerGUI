package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import com.example.App;
import models.*;

import javafx.fxml.Initializable;

public class SignupController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private Label lbl_error;

    @FXML
    private void btnRegistarClicked(MouseEvent event) throws IOException {

        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }
    
}
