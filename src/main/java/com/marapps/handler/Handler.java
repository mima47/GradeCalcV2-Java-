package com.marapps.handler;

import com.marapps.api.Institute;
import com.marapps.api.Kreta;
import com.marapps.api.Mapper;
import com.marapps.api.Token;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Handler {

    Mapper mapper = new Mapper();
    Kreta kreta = new Kreta();
    List<Institute> INSTITUTES = mapper.map_institute_list(kreta.get_school_list());

    public List<Institute> search_institute(String searchTerm){
        List<Institute> list = INSTITUTES;
        return list.stream()
                .filter(item -> item.getName().contains(searchTerm))
                .collect(Collectors.toList());
    }

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

    public String login(String username, String password, Institute institute){
        return mapper.map_token(
          kreta.get_tokens(username, password, institute.getInstituteCode())
        ).getAccess_token();
    }
}
