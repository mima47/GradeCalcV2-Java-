package com.marapps.handler;

import com.marapps.api.Institute;
import com.marapps.api.Kreta;
import com.marapps.api.Mapper;
import com.marapps.api.Token;
import com.marapps.models.User;


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
    static public String username;
    static public String password;
    static public Token token;
    static public User loggedInUser;


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

    public Token login(User user){
        return mapper.map_token(
          kreta.get_tokens(user.getUsername(), user.getPassword(), user.getInstitute().getInstituteCode())
        );
    }
}
