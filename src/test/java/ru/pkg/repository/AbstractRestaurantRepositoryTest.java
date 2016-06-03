package ru.pkg.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Restaurant;

import java.util.Arrays;
import java.util.Collections;

import static ru.pkg.RestaurantTestData.*;

public abstract class AbstractRestaurantRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    protected RestaurantRepository repository;

    @Test
    public void testFindById() throws Exception {
        Restaurant restaurant = repository.findById(START_INDEX);
        MATCHER.assertEquals(TEST_RESTAURANT_1, restaurant);
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        Assert.assertNull(repository.findById(NOT_FOUND_INDEX));
    }

    @Test
    public void testDelete() throws Exception {
        Assert.assertTrue(repository.delete(START_INDEX));
        MATCHER.assertCollectionsEquals(Collections.singletonList(TEST_RESTAURANT_2), repository.findAll());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        Assert.assertFalse(repository.delete(NOT_FOUND_INDEX));
        MATCHER.assertCollectionsEquals(Arrays.asList(TEST_RESTAURANT_1, TEST_RESTAURANT_2), repository.findAll());
    }

    @Test
    public void testAdd() throws Exception {
        Restaurant toCreateRestaurant = new TestRestaurant(null, TEST_RESTAURANT_NEW);
        Restaurant created = repository.save(toCreateRestaurant);
        Assert.assertNotNull(toCreateRestaurant.getId());
        MATCHER.assertEquals(toCreateRestaurant, created);
        MATCHER.assertCollectionsEquals(Arrays.asList(TEST_RESTAURANT_1, created, TEST_RESTAURANT_2), repository.findAll());
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant toUpdateRestaurant = new TestRestaurant(START_INDEX, TEST_RESTAURANT_NEW);
        Restaurant updated = repository.save(toUpdateRestaurant);
        Assert.assertTrue(updated.getId() == START_INDEX);
        MATCHER.assertEquals(toUpdateRestaurant, updated);
        MATCHER.assertCollectionsEquals(Arrays.asList(updated, TEST_RESTAURANT_2), repository.findAll());
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Restaurant toUpdateRestaurant = new TestRestaurant(NOT_FOUND_INDEX, TEST_RESTAURANT_NEW);
        Assert.assertNull(repository.save(toUpdateRestaurant));
        MATCHER.assertCollectionsEquals(Arrays.asList(TEST_RESTAURANT_1, TEST_RESTAURANT_2), repository.findAll());
    }

    @Test
    public void testFindAll() throws Exception {
        MATCHER.assertCollectionsEquals(Arrays.asList(TEST_RESTAURANT_1, TEST_RESTAURANT_2), repository.findAll());
    }

    @Test
    public void testFindAllWithMenus() throws Exception {

    }
}
