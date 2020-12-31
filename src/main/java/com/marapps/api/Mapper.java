package com.marapps.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class Mapper {

    ObjectMapper mapper = new ObjectMapper();

    public List<Institute> map_institute_list(String jsonString){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(jsonString, new TypeReference<List<Institute>>(){});
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    public Token map_token(String jsonString){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        try {
            return mapper.readValue(jsonString, Token.class);
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Evaluation> map_evaluation(String jsonString){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        try {
            return mapper.readValue(jsonString, new TypeReference<List<Evaluation>>(){});
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
