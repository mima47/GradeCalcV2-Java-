module com.marapps {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires okio;
    requires kotlin.stdlib;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;


    opens com.marapps to javafx.fxml;
    opens com.marapps.controllers to javafx.fxml;
    opens com.marapps.api to javafx.fxml;
    exports com.marapps;
    exports com.marapps.controllers;
    exports com.marapps.api;
}