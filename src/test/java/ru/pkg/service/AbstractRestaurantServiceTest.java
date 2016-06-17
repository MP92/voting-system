package ru.pkg.service;

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

    @Test
    public void testAdd() throws Exception {
        Restaurant toCreateRestaurant = TestRestaurantFactory.newIntanceForCreate();
        Restaurant added = service.add(toCreateRestaurant);
        Assert.assertNotNull(toCreateRestaurant.getId());
        MATCHER.assertEquals(toCreateRestaurant, added);
        MATCHER.assertCollectionsEquals(Arrays.asList(RESTAURANT_1, RESTAURANT_2, added), service.findAll());
    }

    @Test
    public void testFindById() throws Exception {
        MATCHER.assertEquals(TestRestaurantFactory.newInstanceWithMenu(RESTAURANT_1, R_1_IN_MENU_DISHES), service.findById(START_INDEX));
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testFindByIdNotFound() throws Exception {
        service.findById(NOT_FOUND_INDEX);
    }

    @Test
    public void testFindAll() throws Exception {
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS_WITHOUT_MENU, service.findAll());
    }

    @Test
    public void testFindAllWithMenu() throws Exception {
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS_WITH_MENU, service.findAllWithMenu());
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant toUpdateRestaurant = TestRestaurantFactory.newIntanceForUpdate();
        service.update(toUpdateRestaurant);
        MATCHER.assertCollectionsEquals(Arrays.asList(RESTAURANT_2, toUpdateRestaurant), service.findAll());
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        Restaurant toUpdateRestaurant = TestRestaurantFactory.newIntanceForUpdateNonexistent();
        service.update(toUpdateRestaurant);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(START_INDEX);
        MATCHER.assertCollectionsEquals(Collections.singletonList(RESTAURANT_2), service.findAll());
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(NOT_FOUND_INDEX);
    }
}