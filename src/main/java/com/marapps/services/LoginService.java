package com.marapps.services;

import com.marapps.App;
import com.marapps.api.Kreta;
import com.marapps.api.Mapper;
import com.marapps.handler.Handler;
import com.marapps.models.User;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LoginService extends Service<Void> {

    User user;
    HBox loadBox;
    PasswordField passwordField;
    CheckBox stayLoggedInCheck;
    TextField userField;

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public void setStayLoggedInCheck(CheckBox stayLoggedInCheck) {
        this.stayLoggedInCheck = stayLoggedInCheck;
    }

    public void setUserField(TextField userField) {
        this.userField = userField;
    }

    public void setLoadBox(HBox loadBox) {
        this.loadBox = loadBox;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> setLoadVisible(true));
                Parent root;
                System.out.println("LoginService.call(): fired");

                //Check if text fields are empty
                //Make text input borders red if they are empty
                if (userField.getText().equals("") || passwordField.getText().equals("") || Handler.selectedInstitute == null) {
                    userField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                    passwordField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                    Platform.runLater(() -> setLoadVisible(false));

                    // Check if login information is correct
                } else if (isLoginCorrect()){
                    System.out.println("LoginService.isLoginCorrect(): Done");

                    serialize();
                    System.out.println("LoginService.serialize(): Done");

                    //After serialization open the dashboard
                    try {
                        System.out.println("Loading fxml/dashboard...");
                        root = App.loadFXML("fxml/dashboard");
                        System.out.println("Loaded");
                        Platform.runLater(() -> {
                            Stage stage = new Stage();
                            stage.setTitle("Dashboard");
                            stage.setScene(new Scene(root));
                            stage.show();
                            passwordField.getScene().getWindow().hide();
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // if login information is incorrect, make the textfields red
                } else {
                    userField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                    passwordField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
                }
                return null;
            }
        };
    }

    private void serialize() {
        System.out.println("LoginService.serialize(): fired");

        Handler.username = user.getUsername();
        Handler.password = user.getPassword();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(Handler.pathToHome + "\\login.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        Handler.loggedInUser = user;
    }

    private boolean isLoginCorrect(){
        // DEBUG
        System.out.println("isLoginCorrect(): fired");

        Mapper mapper = new Mapper();
        Kreta kreta = new Kreta();

        try {
            Handler.token = mapper.map_token(
                    kreta.get_tokens(
                            user.getUsername(),
                            user.getPassword(),
                            user.getInstitute().getInstituteCode())
            );
            System.out.println("Login Correct");
        } catch (Exception e){
            System.out.println("Login incorrect");
            Platform.runLater(() -> setLoadVisible(false));
            Handler.token = null;
        }

        return Handler.token != null;
    }

    private void setLoadVisible(boolean isVisible)
    {
        loadBox.setVisible(isVisible);
    }

}
