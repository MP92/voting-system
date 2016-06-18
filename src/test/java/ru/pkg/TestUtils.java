package ru.pkg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static ru.pkg.web.json.JacksonObjectMapper.getMapper;

public class TestUtils {

    public static String createStringWithLength(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append('a');
        }
        return sb.toString();
    }

    public static ResultMatcher jsonMatcher(Object expected) throws JsonProcessingException {
        return content().json(toJson(expected));
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return getMapper().writeValueAsString(object);
    }

    public static String getContent(ResultActions resultActions) throws UnsupportedEncodingException {
        return resultActions.andReturn().getResponse().getContentAsString();
    }

    public static int getIntFromJson(String json, String fieldName) throws IOException {
        ObjectNode nodes = getMapper().readValue(json, ObjectNode.class);
        return nodes.get(fieldName).intValue();
    }

    public static int getIntFromJson(ResultActions resultActions, String fieldName) throws IOException {
        return getIntFromJson(getContent(resultActions), fieldName);
    }
}
