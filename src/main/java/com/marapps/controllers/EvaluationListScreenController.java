package com.marapps.controllers;

import com.marapps.api.Evaluation;
import com.marapps.handler.Handler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class EvaluationListScreenController {
    public ListView<Evaluation> listView;

    public Label evalLabel;
    public Label weightLabel;
    public Label subjectLabel;
    public Label descriptionLabel;
    public Label typeLabel;
    public Label dateLabel;
    public VBox vbox;

    public void initialize() {
        // setup selection listener
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Tooltip descriptionTooltip = new Tooltip();
            descriptionTooltip.setText(newValue.getTema());

            evalLabel.setText(String.valueOf(newValue.getSzamErtek()));
            weightLabel.setText(newValue.getSulySzazalekErteke() + "%");
            subjectLabel.setText(newValue.getTantargy().get("Nev").toString());
            descriptionLabel.setText(newValue.getTema());
            typeLabel.setText(newValue.getMod().get("Leiras"));
            dateLabel.setText(newValue.getKeszitesDatumaParsed().toLocalDate().toString());

            descriptionLabel.setTooltip(descriptionTooltip);
        });

        // Setup cell names with CellFactory
        listView.setItems(Handler.evaluationList);
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Evaluation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String text = item.getTantargy().get("Nev").toString() + ": " + item.getSzamErtek();
                    setText(text);
                }
            }
        });
    }
}
