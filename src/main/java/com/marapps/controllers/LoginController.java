package com.marapps.controllers;

import com.marapps.handler.Handler;
import com.marapps.models.User;
import com.marapps.services.InstituteListService;
import com.marapps.services.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class LoginController{

    public GridPane gridPane;
    public HBox loadBox;
    @FXML CheckBox stayLoggedInCheck;
    @FXML Button instituteButton;
    @FXML TextField userField;
    @FXML PasswordField passwordField;

    @FXML
    public void handleSubmitButtonAction() {
        LoginService service = new LoginService();
        User user = new User();
        user.setUsername(userField.getText());
        user.setPassword(passwordField.getText());
        user.setInstitute(Handler.selectedInstitute);
        user.setStayLoggedIn(stayLoggedInCheck.isSelected());

        service.setUser(user);
        service.setPasswordField(passwordField);
        service.setUserField(userField);
        service.setStayLoggedInCheck(stayLoggedInCheck);
        service.setLoadBox(loadBox);

        service.start();

    }

    //Open institue select screen
    @FXML
    public void selectInstitute() {
        InstituteListService service = new InstituteListService();
        service.setInstituteButton(instituteButton);
        service.setLoadBox(loadBox);

        service.start();
    }
}
