package com.marapps.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {

    @FXML Button instituteButton;
    @FXML TextField userField;
    @FXML PasswordField passwordField;

    @FXML
    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader();

        try {
            root = fxmlLoader.load(getClass().getResource("/com/marapps/secondary.fxml"));
            Stage stage = new Stage();
            stage.setTitle("asdasdads");
            stage.setScene(new Scene(root, 300, 275));
            stage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void selectInstitute(ActionEvent actionEvent) {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader();

        try {
            root = fxmlLoader.load(getClass().getResource("/com/marapps/selectInstitute.fxml"));
            
            Stage stage = new Stage();
            stage.setTitle("Select Institute");
            stage.setScene(new Scene(root, 640, 480));
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
