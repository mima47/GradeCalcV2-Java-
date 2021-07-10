package com.marapps.services;

import com.marapps.App;
import com.marapps.handler.Handler;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class InstituteListService extends Service<Void> {

    Button instituteButton;
    HBox loadBox;

    public final void setInstituteButton(Button instituteButton){
        this.instituteButton = instituteButton;
    }

    public final Button getInstituteButton(){
        return instituteButton;
    }

    public final void setLoadBox(HBox loadBox){
        this.loadBox = loadBox;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                loadBox.setVisible(true);
                System.out.println("loadFxmlTask: Fired");
                Parent root;
                try {
                    root = App.loadFXML("fxml/selectInstitute");
                    Platform.runLater(() -> {
                        Stage stage = new Stage();
                        stage.setTitle("Select Institute");
                        stage.setScene(new Scene(root, 640, 480));
                        stage.show();
                        //Set button text to selected institute
                        stage.setOnHidden(event -> instituteButton.setText(Handler.selectedInstitute.getName()));
                    });
                } catch (IOException e){
                    e.printStackTrace();
                }
                loadBox.setVisible(false);
                return null;
            }
        };
    }
}
