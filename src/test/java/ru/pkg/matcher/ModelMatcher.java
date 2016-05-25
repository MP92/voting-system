package ru.pkg.matcher;

import org.junit.Assert;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Matcher for entity comparison
 *
 * @param <T> : entity type
 * @param <R> : converted entity type for compare
 */
public class ModelMatcher<T, R> {

    private Function<T, R> converter;

    public ModelMatcher(Function<T, R> converter) {
        this.converter = converter;
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
}
