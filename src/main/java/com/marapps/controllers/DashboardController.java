package com.marapps.controllers;

import com.marapps.App;
import com.marapps.api.Evaluation;
import com.marapps.api.Kreta;
import com.marapps.api.Mapper;
import com.marapps.handler.Handler;
import com.marapps.models.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class DashboardController {

    public MenuBar menubar;
    public Label moneyLabel;
    public Label halfYearMoneyLabel;
    public Label endYearMoneyLabel;
    Handler handler = new Handler();
    List<Evaluation> evaluationList;
    List<String> excludedSubjects = Arrays.asList("testneveles_es_sport", "Magatartas", "Szorgalom");


    public void initialize() {
        Handler.token = handler.login(Handler.loggedInUser);
        Mapper mapper = new Mapper();
        Kreta kreta = new Kreta();
        int money;
        int halfYearMoney;
        int endYearMoney;

        // Get evaluation list
        this.evaluationList = mapper.map_evaluation(
                kreta.get_student_evaluations(
                        Handler.token.getAccess_token(), Handler.loggedInUser.getInstitute().getUrl()
                ));

        // Parse creation date into usable format for each evaluation
        for (Evaluation eval : evaluationList){
            String keszitesDatuma = eval.KeszitesDatuma;
            keszitesDatuma = eval.KeszitesDatuma.substring(0, keszitesDatuma.length() - 1);
            eval.setKeszitesDatumaParsed(LocalDateTime.parse(keszitesDatuma));
        }

        // Filter out non-wanted subjects
        List<Evaluation> filteredEval = evaluationList.stream()
                .filter(item -> !excludedSubjects.contains(((Map)item.getTantargy().get("Kategoria")).get("Nev")))
//                .filter(item -> item.getTipus().get("Uid").contains("1519"))
//                .filter(item -> item.getTipus().get("Uid").contains("1520"))
                .collect(Collectors.toList());
        money = Handler.calculate(Handler.thisMonth(filteredEval));

        // Filter out half and end-year grades
        List<Evaluation> filteredEval2 = evaluationList.stream()
                .filter(item -> !item.getTipus().get("Uid").contains("1519"))
                .filter(item -> !item.getTipus().get("Uid").contains("1520"))
                .collect(Collectors.toList());

        // List for EvalListScreen to use after clicking more info...
        Handler.evaluationList = FXCollections.observableList(Handler.thisMonth(filteredEval2));
        Color color = (money < 0) ? Color.RED : Color.GREEN;

        String moneyString = String.valueOf(money);
        moneyLabel.setTextFill(color);
        moneyLabel.setText(moneyString + " Ft");


        /* Half-year stuff from here
        *
        *
        *
        */

        // Filter evals to half-year
        List<Evaluation> halfYearGradesList = filteredEval.stream()
                .filter(item -> item.getTipus().get("Uid").contains("1519")) // 1519 is the uid for half-year grades
                .collect(Collectors.toList());

        halfYearMoney = Handler.calculateHalfYear(halfYearGradesList);

        moneyString = String.valueOf(halfYearMoney);
        halfYearMoneyLabel.setText(moneyString + " Ft");

        /* End-year stuff from here
        *
        *
        *
        */

        List<Evaluation> endYearGradesList = filteredEval.stream()
                .filter(item -> item.getTipus().get("Uid").contains("1520")) // 1520 is the uid for end-year grades
                .collect(Collectors.toList());

        endYearMoney = Handler.calculateHalfYear(endYearGradesList);

        moneyString = String.valueOf(endYearMoney);
        endYearMoneyLabel.setText(moneyString + " Ft");
    }

    public void logout() {
        //Set user to not stay logged in
        User user = Handler.loggedInUser;
        user.setStayLoggedIn(false);

        //Serialize
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(Handler.pathToHome + "\\login.ser");
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
            root = App.loadFXML("fxml/login");
            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root, 500, 350));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void thisMonthButtonAction() {

        int money;

        for (Evaluation eval : evaluationList){
            String keszitesDatuma = eval.KeszitesDatuma;
            keszitesDatuma = eval.KeszitesDatuma.substring(0, keszitesDatuma.length() - 1);
            eval.setKeszitesDatumaParsed(LocalDateTime.parse(keszitesDatuma));
        }

        List<Evaluation> filteredEval = evaluationList.stream()
                .filter(item -> !excludedSubjects.contains(((Map)item.getTantargy().get("Kategoria")).get("Nev")))
                .filter(item -> !item.getTipus().get("Uid").contains("1519"))
                .filter(item -> !item.getTipus().get("Uid").contains("1520"))
                .collect(Collectors.toList());
        money = Handler.calculate(Handler.thisMonth(filteredEval));
        Handler.evaluationList = FXCollections.observableList(Handler.thisMonth(filteredEval));
        Color color = (money < 0) ? Color.RED : Color.GREEN;

        String moneyString = String.valueOf(money);
        moneyLabel.setTextFill(color);
        moneyLabel.setText(moneyString + " Ft");
    }

    public void lastMonthButtonAction() {
        int money;

        for (Evaluation eval : evaluationList){
            String keszitesDatuma = eval.KeszitesDatuma;
            keszitesDatuma = eval.KeszitesDatuma.substring(0, keszitesDatuma.length() - 1);
            eval.setKeszitesDatumaParsed(LocalDateTime.parse(keszitesDatuma));
        }

        List<Evaluation> filteredEval = evaluationList.stream()
                .filter(item -> !excludedSubjects.contains(((Map)item.getTantargy().get("Kategoria")).get("Nev")))
                .filter(item -> !item.getTipus().get("Uid").contains("1519"))
                .filter(item -> !item.getTipus().get("Uid").contains("1520"))
                .collect(Collectors.toList());
        money = Handler.calculate(Handler.beforeMonth(filteredEval));
        Handler.evaluationList = FXCollections.observableList(Handler.beforeMonth(filteredEval));
        Color color = (money < 0) ? Color.RED : Color.GREEN;

        String moneyString = String.valueOf(money);
        moneyLabel.setTextFill(color);
        moneyLabel.setText(moneyString + " Ft");
    }

    public void moreInfo(ActionEvent event) throws IOException{
        // Get user data of button (in this case, the path to the fxml files)
        Node node = (Node) event.getSource();
        System.out.println(node.getId());
        String data = (String)node.getUserData();

        Parent root;
        root = App.loadFXML(data);
        Stage stage = new Stage();
        stage.setTitle("Evaluation List");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    //TODO: Statistics screen (all money in the year, number of grades, etc.) to menubar
    //TODO: Half & End-year more info button
}
