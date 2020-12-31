package com.marapps.controllers;

import com.marapps.api.Institute;
import com.marapps.handler.Handler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SelectInstituteController {

    public ListView<String> listView;
    public TextField searchField;
    public static Button selectButton;

    Handler handler = new Handler();

    public void initialize(){
        List<String> names = handler.institute_name_list_string();
        ObservableList<String> list = FXCollections.observableArrayList(names);
        listView.setItems(list);
    }

    public void searchPressed(ActionEvent actionEvent) {
        String searchText = searchField.getText();
        List<Institute> instituteList = handler.search_institute(searchText);
        List<String> instituteNames = new ArrayList<>();
        for (Institute inst : instituteList){
            instituteNames.add(inst.getName());
        }
        ObservableList<String> list = FXCollections.observableArrayList(instituteNames);
        listView.setItems(list);
        listView.refresh();
    }

    public void selectPressed(ActionEvent actionEvent){
        String selectedInstituteName = listView.getSelectionModel().getSelectedItem();

        Institute selectedInst = handler.search_institute(selectedInstituteName).get(0);
        selectedInst.print();
        LoginController
    }
}
