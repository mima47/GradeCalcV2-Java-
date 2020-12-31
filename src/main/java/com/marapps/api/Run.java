package com.marapps.api;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Run {
    public static void main(String[] args) throws IOException {
        Kreta kreta = new Kreta();
        Mapper mapper = new Mapper();

        Token token = mapper.map_token(kreta.get_tokens("72461595822","2004-08-26", "paszc-faller"));

        List<Institute> instituteList = mapper.map_institute_list(kreta.get_school_list());
        List<Institute> filtered = instituteList.stream()
                .filter(item -> item.name.contains("Faller"))
                .collect(Collectors.toList());

        for (Institute institute : filtered) {
            institute.print();
        }

        Institute faller = filtered.get(0);

        List<Evaluation> evaluationList = mapper.map_evaluation(kreta.get_student_evaluations(token.getAccess_token(), faller.getUrl()));

        for (Evaluation eval : evaluationList){
//            System.out.println(eval.getErtekeloTanarNeve());
//            System.out.println(eval.getErtekFajta());
//            System.out.println(eval.getJelleg());
//            System.out.println(eval.getTantargy());
//            System.out.println(eval.getTantargy());
//            System.out.println(eval.getTantargy());
//            System.out.println("------------------");
//            System.out.println("");
        }
    }
}
