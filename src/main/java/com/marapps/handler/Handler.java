package com.marapps.handler;

import com.marapps.api.*;
import com.marapps.models.User;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//This is the file responsible for connecting different classes and passing data between them.
//Hence the static variables

public class Handler {

    Mapper mapper = new Mapper();
    Kreta kreta = new Kreta();
    List<Institute> INSTITUTES = mapper.map_institute_list(kreta.get_school_list());

    static public Institute selectedInstitute = null;
    static public ObservableList<Evaluation> evaluationList;
    static public String username;
    static public String password;
    static public Token token;
    static public User loggedInUser;
    static public String pathToHome;



    public List<Institute> search_institute(String searchTerm){
        List<Institute> list = INSTITUTES;
        return list.stream()
                .filter(item -> item.getName().contains(searchTerm))
                .collect(Collectors.toList());
    }

    //Returns a single institute
    public Institute select_institute(List<Institute> instituteList, int index){
        return instituteList.get(index);
    }

    public List<String> institute_name_list_string(){
        List<String> instNamesList = new ArrayList<>();
        List<Institute> instList = INSTITUTES;
        for (Institute inst : instList){
            instNamesList.add(inst.getName());
        }
        return instNamesList;
    }

    public Token login(User user) {
        try {
            return mapper.map_token(
                    kreta.get_tokens(user.getUsername(), user.getPassword(), user.getInstitute().getInstituteCode())
            );
        } catch (Exception e){
            return null;
        }
    }

    public static int calculate(List<Evaluation> evaluationList){
        List<Integer> evalValues = new ArrayList<>();
        int sum = 0;

        for (Evaluation eval : evaluationList){
            int value = 100;
            int weight = eval.getSulySzazalekErteke() / 100;

            switch (eval.getSzamErtek()){
                case 5:
                    value = value*5*weight;
                    evalValues.add(value);
                    break;

                case 4:
                    value = value*4*weight;
                    evalValues.add(value);
                    break;

                case 1:
                    value = value*10*weight;
                    value *= -1;
                    evalValues.add(value);
                    break;
            }
//            System.out.println("Subject: "+eval.getTantargy());
//            System.out.println("Weight: "+eval.getSulySzazalekErteke());
//            System.out.println("Weight/100: "+weight);
//            System.out.println("Number: "+eval.getSzamErtek());
//            System.out.println("Value: "+value);
//            System.out.println("");
        }
        for (int i : evalValues){
            sum += i;
        }
        return sum;
    }

    public static int calculateHalfYear(List<Evaluation> evaluationList) {
        List<Integer> evalValues = new ArrayList<>();
        int sum = 0;

        for (Evaluation eval : evaluationList){
            int value = 100;

            switch (eval.getSzamErtek()){
                case 5:
                    value = value*5*5;
                    evalValues.add(value);
                    break;

                case 4:
                    value = value*5*4;
                    evalValues.add(value);
                    break;
            }
        }
        for (int i : evalValues){
            sum += i;
        }
        return sum;
    }

    // Returns a list with evaluations from the month before now.
    public static  List<Evaluation> beforeMonth(List<Evaluation> evaluationList) {
        LocalDate nowDate = LocalDate.now();
        LocalDate fromMonth = LocalDate.of(nowDate.getYear(), nowDate.getMonthValue() - 2, 1);
        LocalDate fromDate = LocalDate.of(nowDate.getYear(), nowDate.getMonthValue() - 2, fromMonth.lengthOfMonth());
        LocalDate toDate = LocalDate.of(nowDate.getYear(), nowDate.getMonthValue(), 1);

        // Filtering
        return evaluationList.stream()
                .filter(
                        item -> item.getKeszitesDatumaParsed().toLocalDate().isAfter(fromDate)
                                && item.getKeszitesDatumaParsed().toLocalDate().isBefore(toDate)
                )
                .collect(Collectors.toList());
    }

    // Returns a list with evaluations from this month.
    public static  List<Evaluation> thisMonth(List<Evaluation> evaluationList) {
        LocalDate nowDate = LocalDate.now();
        LocalDate fromDate = LocalDate.of
                (
                        nowDate.getYear(), nowDate.getMonthValue() - 1
                        ,nowDate.getMonth().minus(1).length(nowDate.isLeapYear())
                );

        // Filtering
        return evaluationList.stream()
                .filter(
                        item -> item.getKeszitesDatumaParsed().toLocalDate().isAfter(fromDate)
                )
                .collect(Collectors.toList());

    }

    public static  List<Evaluation> customMonth(List<Evaluation> evaluationList, LocalDate fromDate, LocalDate toDate){
        return evaluationList.stream().filter(
                item -> item.getKeszitesDatumaParsed().toLocalDate().isAfter(fromDate)
                        && item.getKeszitesDatumaParsed().toLocalDate().isBefore(toDate)
        )
                .collect(Collectors.toList());
    }
}
