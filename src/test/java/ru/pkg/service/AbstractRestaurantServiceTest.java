package ru.pkg.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Restaurant;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.Arrays;
import java.util.Collections;

import static ru.pkg.testdata.RestaurantTestData.*;

public abstract class AbstractRestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @After
    public void tearDown() throws Exception {
        cacheManager.getCache("restaurants").clear();
    }

    @Test
    public void testAdd() throws Exception {
        Restaurant toCreateRestaurant = TestRestaurantFactory.newInstanceForCreate();
        Restaurant added = service.add(toCreateRestaurant);
        Assert.assertNotNull(toCreateRestaurant.getId());
        MATCHER.assertEquals(toCreateRestaurant, added);
        MATCHER.assertCollectionsEquals(Arrays.asList(RESTAURANT_1, RESTAURANT_2, added), service.findAll());
    }

    @Test
    public void testFindById() throws Exception {
        MATCHER.assertEquals(RESTAURANT_1, service.findById(RESTAURANT_1_ID));
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testFindByIdNotFound() throws Exception {
        service.findById(NOT_FOUND_INDEX);
    }

    @Test
    public void testFindAll() throws Exception {
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS, service.findAll());
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant toUpdateRestaurant = TestRestaurantFactory.newInstanceForUpdate();
        service.update(toUpdateRestaurant);
        MATCHER.assertCollectionsEquals(Arrays.asList(RESTAURANT_2, toUpdateRestaurant), service.findAll());
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        Restaurant toUpdateRestaurant = TestRestaurantFactory.newInstanceForUpdateNonexistent();
        service.update(toUpdateRestaurant);
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS, service.findAll());
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(RESTAURANT_1_ID);
        MATCHER.assertCollectionsEquals(Collections.singletonList(RESTAURANT_2), service.findAll());
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(NOT_FOUND_INDEX);
    }
}