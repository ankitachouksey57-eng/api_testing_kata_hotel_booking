package com.booking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SupportLibrary {

    public static <T> String convertJavaObjectToJson(T requestObject) throws  JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        json = objectMapper.writeValueAsString(requestObject);
        return json;
    }
}
