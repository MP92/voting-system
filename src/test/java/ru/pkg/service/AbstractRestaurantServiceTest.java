package ru.pkg.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Restaurant;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.Arrays;
import java.util.Collections;

import static ru.pkg.RestaurantTestData.*;

public abstract class AbstractRestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService service;

    @Test
    public void testFindById() throws Exception {
        MATCHER.assertEquals(TEST_RESTAURANT_1, service.findById(START_INDEX));
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testFindByIdNotFound() throws Exception {
        service.findById(NOT_FOUND_INDEX);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(START_INDEX);
        MATCHER.assertCollectionsEquals(Collections.singletonList(TEST_RESTAURANT_2), service.findAll());
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(NOT_FOUND_INDEX);
    }

    @Test
    public void testAdd() throws Exception {
        TestRestaurant toCreateRestaurant = new TestRestaurant(null, TEST_RESTAURANT_NEW);
        Restaurant added = service.add(toCreateRestaurant);
        Assert.assertNotNull(toCreateRestaurant.getId());
        MATCHER.assertEquals(toCreateRestaurant, added);
        MATCHER.assertCollectionsEquals(Arrays.asList(TEST_RESTAURANT_1, added, TEST_RESTAURANT_2), service.findAll());
    }

    @Test
    public void testUpdate() throws Exception {
        TestRestaurant toUpdateRestaurant = new TestRestaurant(START_INDEX, TEST_RESTAURANT_NEW);
        service.update(toUpdateRestaurant);
        MATCHER.assertCollectionsEquals(Arrays.asList(toUpdateRestaurant, TEST_RESTAURANT_2), service.findAll());
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        TestRestaurant toUpdateRestaurant = new TestRestaurant(NOT_FOUND_INDEX, TEST_RESTAURANT_NEW);
        service.update(toUpdateRestaurant);
    }

    @Test
    public void testFindAll() throws Exception {
        MATCHER.assertCollectionsEquals(Arrays.asList(TEST_RESTAURANT_1, TEST_RESTAURANT_2), service.findAll());
    }
}