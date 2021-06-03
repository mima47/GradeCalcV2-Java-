package com.marapps.api;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// This is a test file, it's not connected to anything.
public class Run {
    public static void main(String[] args) {
        Kreta kreta = new Kreta();
        Mapper mapper = new Mapper();

        Token token = mapper.map_token(kreta.get_tokens("72461595822","2004-08-26", "paszc-faller"));

        List<Institute> instituteList = mapper.map_institute_list(kreta.get_school_list());
        List<Institute> filtered = instituteList.stream()
                .filter(item -> item.name.contains("Faller"))
                .collect(Collectors.toList());

//        for (Institute institute : filtered) {
//            institute.print();
//        }

        Institute faller = filtered.get(0);

        List<Evaluation> evaluationList = mapper.map_evaluation(kreta.get_student_evaluations(token.getAccess_token(), faller.getUrl()));


        for (Evaluation eval : evaluationList){
            String keszitesDatuma = eval.KeszitesDatuma;
            keszitesDatuma = eval.KeszitesDatuma.substring(0, keszitesDatuma.length() - 1);
            eval.setKeszitesDatumaParsed(LocalDateTime.parse(keszitesDatuma));
        }



        System.out.println(((Map)evaluationList.get(2).getTantargy().get("Kategoria")).get("Nev"));

//        List<Evaluation> filteredEvals = evaluationList.stream()
//                .filter(item -> item.getKeszitesDatumaParsed().isAfter(localDateTime))
//                .collect(Collectors.toList());



//        for (Evaluation eval : evaluationList){
//            System.out.println(eval.getErtekeloTanarNeve());
//            System.out.println(eval.getErtekFajta());
//            System.out.println(eval.getJelleg());
//            System.out.println(eval.getTantargy());
//            System.out.println(eval.getTantargy());
//            System.out.println(eval.getTantargy());
//            System.out.println("------------------");
//            System.out.println("");
//        }
    }

    // Returns a list with evaluations from the month before now.
    static List<Evaluation> beforeMonth(List<Evaluation> evaluationList) {
        LocalDate nowDate = LocalDate.now();
        LocalDate fromDate = LocalDate.of(nowDate.getYear(), nowDate.getMonthValue() - 2, 31);
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
    static List<Evaluation> thisMonth(List<Evaluation> evaluationList) {
        LocalDate nowDate = LocalDate.now();
        LocalDate fromDate = LocalDate.of(nowDate.getYear(), nowDate.getMonthValue() - 1, 31);

        // Filtering
        return evaluationList.stream()
                .filter(
                        item -> item.getKeszitesDatumaParsed().toLocalDate().isAfter(fromDate)
                )
                .collect(Collectors.toList());

    }

    static List<Evaluation> customMonth(List<Evaluation> evaluationList, LocalDate fromDate, LocalDate toDate){
        return evaluationList.stream().filter(
                item -> item.getKeszitesDatumaParsed().toLocalDate().isAfter(fromDate)
                && item.getKeszitesDatumaParsed().toLocalDate().isBefore(toDate)
        )
        .collect(Collectors.toList());
    }
}
