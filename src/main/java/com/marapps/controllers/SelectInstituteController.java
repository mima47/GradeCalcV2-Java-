package com.marapps.controllers;

import com.marapps.api.Institute;
import com.marapps.handler.Handler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SelectInstituteController {

    public ListView<String> listView;
    public TextField searchField;
    public Button selectButton;

    Handler handler = new Handler();

    public void initialize(){
        //Populate the list with institutes
        List<String> names = handler.institute_name_list_string();
        ObservableList<String> list = FXCollections.observableArrayList(names);
        listView.setItems(list);

        //Pressing enter in search field has same effect as clicking on search button
        searchField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)){
                searchPressed();
            }
        });

        //Double clicking on list item has same effect as clicking on search button
        listView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                selectPressed();
            }
        });
    }

    public void searchPressed() {
        //Refresh list with filtered results from searchField
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

    public void selectPressed(){
        //Select the institute and store it in the handler
        String selectedInstituteName = listView.getSelectionModel().getSelectedItem();

        Institute selectedInst = handler.search_institute(selectedInstituteName).get(0);
        selectedInst.print();
        Handler.selectedInstitute = selectedInst;
        Stage stage = (Stage) selectButton.getScene().getWindow();
        stage.close();
    }
}
