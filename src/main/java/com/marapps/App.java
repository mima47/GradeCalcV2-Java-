package com.marapps;

import com.marapps.handler.Handler;
import com.marapps.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class App extends Application {

    boolean isLoggedIn;
    boolean isConnected;
    Scene scene;


    public void init() {
        if (checkConnection()){
            this.isConnected = true;

            String home = System.getenv("APPDATA");
            File configHome = new File(home, ".gradeCalc").getAbsoluteFile();
            configHome.mkdirs();
            Handler.pathToHome = new File(home, ".gradeCalc").getAbsoluteFile().getAbsolutePath();

            //Deserialize last logged in user (if any)
            //No check needed for file, because if there is no file, the exception is caught (IOException)
            User user;
            try {
                FileInputStream fileInputStream = new FileInputStream(Handler.pathToHome + "\\login.ser");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                user = (User) objectInputStream.readObject();
                //After deserialization, the user is placed in the handler
                Handler.loggedInUser = user;
                this.isLoggedIn = user.getStayLoggedIn();

                Handler handler = new Handler();
                Handler.token = handler.login(Handler.loggedInUser);
                if (this.isLoggedIn){
                    loadDashboard();
                } else {
                    loadLogin();
                }
            } catch (IOException e) {
                loadLogin();
                this.isLoggedIn = false;
                System.out.println("No previous user file found!");

            } catch (ClassNotFoundException e) {
                loadLogin();
                this.isLoggedIn = false;
                System.out.println("Class not found");
                e.printStackTrace();
            }

        } else {
            this.isConnected = false;
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        if (this.isConnected) {

            if (this.isLoggedIn) {
                stage.setScene(scene);
                stage.setTitle("Dashboard");
            } else {
                stage.setScene(scene);
                stage.setTitle("Login");
            }
        } else {
            scene = new Scene(loadFXML("fxml/noConnection"), 250, 50);
            stage.setScene(scene);
            stage.setTitle("No internet connection!");
        }
        stage.show();
    }

    public void loadDashboard() {
        try {
            scene = new Scene(loadFXML("fxml/dashboard"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLogin() {
        try {
            scene = new Scene(loadFXML("fxml/login"), 500, 350);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean checkConnection() {
        try {
            URL url = new URL("https://intezmenykereso.e-kreta.hu/");
            URLConnection conn = url.openConnection();
            conn.connect();
            this.isConnected = true;
            return true;
        } catch (IOException e){
            this.isConnected = false;
           return false;
        }
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", AppPreloader.class.getCanonicalName());
        Application.launch(App.class, args);
    }

}