package com.nicok.pathguide.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class SerializeWrapper {

    private ObjectMapper mapper = new ObjectMapper();

    public <T> T deserialize(String serialized, Class<T> clazz) throws IOException {
        return mapper.readValue(serialized, clazz);
    }

    public <T> String serialize(T deserialized) throws JsonProcessingException {
        return mapper.writeValueAsString(deserialized);
    }

}
