package org.se06203.besgtn.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.InstantMillisecondPrecisionSerializer;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@UtilityClass
public class ObjectMapperHelper {

    public static final ObjectMapper objectMapper;
    private static final String PARSE_OBJECT_ERROR_MESSAGE = "Can't parse object from json string: {} - {}";
    private static final String WRITE_OBJECT_ERROR_MESSAGE = "Can't write value as string from object: {}";

    static {
        var offsetTimeUtcModule = new SimpleModule();
        var javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(Instant.class, new InstantMillisecondPrecisionSerializer());

        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(javaTimeModule)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                .registerModule(offsetTimeUtcModule);
    }

    public static String print(Object jsonObject) {
        try {
            return objectMapper.writeValueAsString(jsonObject);
        } catch (Exception ex) {
            log.error(WRITE_OBJECT_ERROR_MESSAGE, jsonObject);
            return null;
        }
    }

    public static String prettyPrint(Object jsonObject) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (Exception ex) {
            log.error(WRITE_OBJECT_ERROR_MESSAGE, jsonObject);
            return null;
        }
    }

    public static String prettyPrint(byte[] bytes) {
        try {
            var jsonNode = objectMapper.reader().readTree(bytes);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception ex) {
            log.error(WRITE_OBJECT_ERROR_MESSAGE);
            return null;
        }
    }

    public static <C extends Collection<?>, T> Collection<T> parseToCollection(String jsonString,
            Class<C> collectionClazz, Class<T> targetClazz) {
        try {
            JavaType type = objectMapper.getTypeFactory()
                    .constructCollectionType(collectionClazz, targetClazz);
            return objectMapper.readValue(jsonString, type);
        } catch (JsonProcessingException ex) {
            log.error(PARSE_OBJECT_ERROR_MESSAGE, jsonString, targetClazz.getName());
            return Collections.emptyList();
        }
    }

    public static <T> Collection<T> parseToList(String jsonString, Class<T> targetClazz) {
        try {
            JavaType type = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, targetClazz);
            return objectMapper.readValue(jsonString, type);
        } catch (JsonProcessingException ex) {
            log.error(PARSE_OBJECT_ERROR_MESSAGE, jsonString, targetClazz.getName());
            return Collections.emptyList();
        }
    }

    public static <T> T parseToObject(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException ex) {
            log.error(PARSE_OBJECT_ERROR_MESSAGE, jsonString, clazz);
            return null;
        }
    }

    public static <T> T parseToObject(Object object, Class<T> clazz) {
        return objectMapper.convertValue(object, clazz);
    }
}
