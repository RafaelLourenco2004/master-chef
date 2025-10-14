package com.example.demo.Utils;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParsing {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <E> List<E> parseJson(String json, Class<E> clas) throws Exception {
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clas));
    }
}
