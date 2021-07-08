package com.marapps.controllers;

import com.marapps.App;
import com.marapps.handler.Handler;
import com.marapps.models.User;
import com.marapps.threads.Serialize;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController{

    public GridPane gridPane;
    public HBox loadBox;
    @FXML CheckBox stayLoggedInCheck;
    @FXML Button instituteButton;
    @FXML TextField userField;
    @FXML PasswordField passwordField;

    @FXML
    public void handleSubmitButtonAction() {
        //TODO: Implement loading indicator (doesn't have to indicate progress)
        Parent root;

        //Check if text fields are empty
        //Make text input borders red if they are empty
        if (userField.getText().equals("") || passwordField.getText().equals("") || Handler.selectedInstitute == null) {
            userField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            passwordField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");

            // Check if login information is correct
        } else if (isLoginCorrect()){

            serializeInThread();

            //After serialization open the dashboard
            try {
                root = App.loadFXML("fxml/dashboard");
                Stage stage = new Stage();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(root, 500, 250));
                stage.show();
                instituteButton.getScene().getWindow().hide();

            } catch (IOException e) {
                e.printStackTrace();
            }
            // if login information is incorrect, make the textfields red
        } else {
            userField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            passwordField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        }
    }
    public boolean isLoginCorrect(){
        User user = new User();
        Handler handler = new Handler();

        user.setUsername(userField.getText());
        user.setPassword(passwordField.getText());
        user.setInstitute(Handler.selectedInstitute);
        user.setStayLoggedIn(stayLoggedInCheck.isSelected());

        return handler.login(user) != null;
    }

    public void serializeInThread() {
        User user = new User();
        user.setUsername(userField.getText());
        user.setPassword(passwordField.getText());
        user.setInstitute(Handler.selectedInstitute);
        user.setStayLoggedIn(stayLoggedInCheck.isSelected());

        Runnable run = new Serialize(user);
        Thread thread = new Thread(run);
        thread.start();
        while (thread.isAlive()){
            System.out.println("Serializing...");
        }
    }

    //Open institue select screen
    @FXML
    public void selectInstitute() {
        Parent root;

        try {
//            root = FXMLLoader.load(getClass().getResource("/com/marapps/fxml/selectInstitute.fxml"));
            root = App.loadFXML("fxml/selectInstitute");
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
