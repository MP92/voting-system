package ru.pkg.web.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDateTime;

public class JacksonObjectMapper extends ObjectMapper {

    private static final ObjectMapper MAPPER = new JacksonObjectMapper();

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    public JacksonObjectMapper() {
//        registerModule(new Hibernate5Module());

        SimpleModule customModule = new SimpleModule("customModule");
        customModule.addSerializer(new JsonLocalDateTimeConverter.LocalDateTimeSerializer());
        customModule.addDeserializer(LocalDateTime.class, new JsonLocalDateTimeConverter.LocalDateTimeDeserializer());
        registerModule(customModule);

        setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
