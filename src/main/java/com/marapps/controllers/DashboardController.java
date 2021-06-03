package com.marapps.controllers;

import com.marapps.api.Evaluation;
import com.marapps.api.Kreta;
import com.marapps.api.Mapper;
import com.marapps.handler.Handler;
import com.marapps.models.User;
import javafx.fxml.FXMLLoader;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class DashboardController {

    public MenuBar menubar;
    public Label moneyLabel;
    Handler handler = new Handler();
    List<Evaluation> evaluationList;


    public void initialize() {
        Handler.token = handler.login(Handler.loggedInUser);
        Mapper mapper = new Mapper();
        Kreta kreta = new Kreta();
        int money;

        this.evaluationList = mapper.map_evaluation(
                kreta.get_student_evaluations(
                        Handler.token.getAccess_token(), Handler.loggedInUser.getInstitute().getUrl()
                ));

        for (Evaluation eval : evaluationList){
            String keszitesDatuma = eval.KeszitesDatuma;
            keszitesDatuma = eval.KeszitesDatuma.substring(0, keszitesDatuma.length() - 1);
            eval.setKeszitesDatumaParsed(LocalDateTime.parse(keszitesDatuma));
        }

        List<String> subjects = Arrays.asList("testneveles_es_sport");

        List<Evaluation> filteredEval = evaluationList.stream()
                .filter(item -> !subjects.contains(((Map)item.getTantargy().get("Kategoria")).get("Nev")))
                .collect(Collectors.toList());
        money = Handler.calculate(Handler.thisMonth(filteredEval));
        Color color = (money < 0) ? Color.RED : Color.GREEN;

        String moneyString = String.valueOf(money);
        moneyLabel.setTextFill(color);
        moneyLabel.setText(moneyString + " Ft");
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
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/marapps/fxml/login.fxml")));
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

        List<String> subjects = Arrays.asList("testneveles_es_sport");

        List<Evaluation> filteredEval = evaluationList.stream()
                .filter(item -> !subjects.contains(((Map)item.getTantargy().get("Kategoria")).get("Nev")))
                .collect(Collectors.toList());
        money = Handler.calculate(Handler.thisMonth(filteredEval));
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

        List<String> subjects = Arrays.asList("testneveles_es_sport");

        List<Evaluation> filteredEval = evaluationList.stream()
                .filter(item -> !subjects.contains(((Map)item.getTantargy().get("Kategoria")).get("Nev")))
                .collect(Collectors.toList());
        money = Handler.calculate(Handler.beforeMonth(filteredEval));
        Color color = (money < 0) ? Color.RED : Color.GREEN;

        String moneyString = String.valueOf(money);
        moneyLabel.setTextFill(color);
        moneyLabel.setText(moneyString + " Ft");
    }
}
