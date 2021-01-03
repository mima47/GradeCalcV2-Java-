package com.marapps;

import com.marapps.handler.Handler;
import com.marapps.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        //Deserialize last logged in user (if any)
        //No check needed for file, because if there is no file, the exception is caught (IOException)
        User user = new User();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/com/marapps/serial/login.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            user = (User) objectInputStream.readObject();
            //After deserialization, the user is placed in the handler
            Handler.loggedInUser = user;
        } catch (IOException e){
            e.printStackTrace();

        } catch (ClassNotFoundException e){
            System.out.println("Class not found");
            e.printStackTrace();
        }
        if (!user.getStayLoggedIn()) {
            scene = new Scene(loadFXML("fxml/login"), 500, 350);
            stage.setScene(scene);
            stage.setTitle("Login");
        } else {
            scene = new Scene(loadFXML("fxml/dashboard"), 500, 350);
            stage.setScene(scene);
            stage.setTitle("Dashboard");
        }
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}