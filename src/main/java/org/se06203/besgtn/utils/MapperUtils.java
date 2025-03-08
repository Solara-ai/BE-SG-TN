package org.se06203.besgtn.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class MapperUtils {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String EMPTY = "";

    public static <T> String toJsonString(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            log.error("json parse error", ex);
            return EMPTY;
        }
    }
}