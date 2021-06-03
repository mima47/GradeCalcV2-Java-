package com.marapps.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Objects;

import com.marapps.App;
import com.marapps.handler.Handler;

import com.marapps.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML CheckBox stayLoggedInCheck;
    @FXML Button instituteButton;
    @FXML TextField userField;
    @FXML PasswordField passwordField;

    @FXML
    public void handleSubmitButtonAction() {
        Parent root;

        //Check if text fields are empty
        //Make text input borders red if they are empty
        if (userField.getText().equals("") || passwordField.getText().equals("") || Handler.selectedInstitute == null) {
            userField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            passwordField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        } else {
            serialize();

            //After serialization open the dashboard
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/marapps/fxml/dashboard.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(root, 500, 250));
                stage.show();
                instituteButton.getScene().getWindow().hide();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void serialize() {
        User user = new User();

        user.setUsername(userField.getText());
        user.setPassword(passwordField.getText());
        user.setInstitute(Handler.selectedInstitute);
        user.setStayLoggedIn(stayLoggedInCheck.isSelected());

        Handler.username = user.getUsername();
        Handler.password = user.getPassword();
        //The institute variable in Handler is already defined at SelectInstituteController

        //Serialize
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/com/marapps/serial/login.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        Handler.loggedInUser = user;
    }

    //Open institue select screen
    @FXML
    public void selectInstitute() {
        Parent root;

        try {
//            root = FXMLLoader.load(getClass().getResource("/com/marapps/fxml/selectInstitute.fxml"));
            root = App.loadFXML("/com/marapps/fxml/selectInstitute");
            Stage stage = new Stage();
            stage.setTitle("Select Institute");
            stage.setScene(new Scene(root, 640, 480));
            stage.show();
            //Set button text to selected institute
            stage.setOnHidden(event -> instituteButton.setText(Handler.selectedInstitute.getName()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
