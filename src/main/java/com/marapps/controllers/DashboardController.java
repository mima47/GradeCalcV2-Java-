package com.marapps.controllers;

import com.marapps.handler.Handler;
import com.marapps.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;



public class DashboardController {

    public MenuBar menubar;
    Handler handler = new Handler();
    private static Scene scene;

    public void initialize(){
        Handler.token = handler.login(Handler.loggedInUser);
    }

    public void logout() {
        //Set user to not stay logged in
        User user = Handler.loggedInUser;
        user.setStayLoggedIn(false);

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

        //Close dashboard and open login window
        menubar.getScene().getWindow().hide();

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/marapps/fxml/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root, 500, 350));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
