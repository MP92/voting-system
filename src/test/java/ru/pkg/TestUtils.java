package ru.pkg;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.pkg.model.User;
import ru.pkg.utils.TimeUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.pkg.web.json.JsonUtil.*;

public class TestUtils {
    private static final Logger LOG = LoggerFactory.getLogger(TestUtils.class);

    public static String createStringWithLength(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append('a');
        }
        return sb.toString();
    }

    public static ResultMatcher jsonMatcher(Object expected) {
        return content().json(toJson(expected));
    }

    public static String toJson(Object object) {
        return writeValue(object);
    }

    public static String getContent(ResultActions resultActions) throws UnsupportedEncodingException {
        return resultActions.andReturn().getResponse().getContentAsString();
    }

    public static int getIntFromJson(String json, String fieldName) {
        return readValue(json, ObjectNode.class).get(fieldName).intValue();
    }

    public static int getIntFromJson(ResultActions resultActions, String fieldName) throws IOException {
        return getIntFromJson(getContent(resultActions), fieldName);
    }

    public static <T> T readFromJson(ResultActions resultActions, Class<T> clazz) throws UnsupportedEncodingException {
        return readValue(getContent(resultActions), clazz);
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getName(), user.getPassword());
    }

    public static MockHttpServletRequestBuilder withParamsFromBean(MockHttpServletRequestBuilder req, Object bean)  {
        try {
            for (Field field : bean.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(bean);
                if (value != null) {
                    req.param(field.getName(), String.valueOf(value));
                }
            }
        } catch (IllegalAccessException e) {
            LOG.warn("Can't get access to bean " + bean);
            e.printStackTrace();
        }
        return req;
    }

    public static void setFakeVoteTimeBound() {
        try {
            Field hourLimit = TimeUtil.class.getDeclaredField("HOUR_LIMIT");
            hourLimit.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(hourLimit, hourLimit.getModifiers() & ~Modifier.FINAL);
            hourLimit.set(null, LocalTime.now().plusHours(1));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOG.warn("Can't modify TimeUtil.HOUR_LIMIT value");
        }
    }
}
