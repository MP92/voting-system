package ru.pkg.matcher;

import org.junit.Assert;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.pkg.web.json.JsonUtil;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Matcher for entity comparison
 *
 * @param <T> : entity type
 * @param <R> : converted entity type for compare
 */
public class ModelMatcher<T, R> {

    private Function<T, R> converter;
    private Class<T> entityClass;

    public ModelMatcher(Function<T, R> converter, Class<T> entityClass) {
        this.converter = converter;
        this.entityClass = entityClass;
    }

    public void assertEquals(T expected, T actual) {
        Assert.assertEquals(converter.apply(expected), converter.apply(actual));
    }

    public void assertCollectionsEquals(Collection<T> expected, Collection<T> actual) {
        Assert.assertEquals(map(expected, converter), map(actual, converter));
    }

    private static <S, T> Collection<T> map(Collection<S> collection, Function<S, T> converter) {
        return collection.stream().map(converter).collect(Collectors.toList());
    }

    public ResultMatcher contentMatcher(T expect) {
        return content().string(
                new TestMatcher<T>(expect) {
                    @Override
                    protected boolean compare(T expected, String body) {
                        R actualForCompare = converter.apply(fromJsonValue(body));
                        R expectedForCompare = converter.apply(expected);
                        return expectedForCompare.equals(actualForCompare);
                    }
                });
    }

    public final ResultMatcher contentListMatcher(List<T> expected) {
        return content().string(new TestMatcher<List<T>>(expected) {
            @Override
            protected boolean compare(List<T> expected, String actual) {
                Collection<R> actualList = map(fromJsonValues(actual), converter);
                Collection<R> expectedList = map(expected, converter);
                return expectedList.equals(actualList);
            }
        });
    }

    private T fromJsonValue(String json) {
        return JsonUtil.readValue(json, entityClass);
    }

    private Collection<T> fromJsonValues(String json) {
        return JsonUtil.readValues(json, entityClass);
    }
}
