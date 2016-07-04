package ru.pkg.matcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import static ru.pkg.web.json.JacksonObjectMapper.*;

abstract public class TestMatcher<T> extends BaseMatcher<String> {
    protected T expected;

    public TestMatcher(T expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object actual) {
        return compare(expected, (String) actual);
    }

    abstract protected boolean compare(T expected, String actual);

    @Override
    public void describeTo(Description description) {
        try {
            description.appendText(getMapper().writeValueAsString(expected));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
