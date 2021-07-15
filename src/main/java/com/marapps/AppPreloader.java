package com.marapps;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppPreloader extends Preloader {
    Stage stage;

    private Scene createPreloaderScene() throws IOException  {
        return new Scene(App.loadFXML("fxml/splash"));
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createPreloaderScene());
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }
}