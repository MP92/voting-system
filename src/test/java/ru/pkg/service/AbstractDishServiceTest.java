package ru.pkg.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.testdata.RestaurantTestData;
import ru.pkg.model.Dish;
import ru.pkg.utils.exception.DishNotFoundException;

import java.util.Arrays;
import java.util.Collections;

import static ru.pkg.testdata.DishTestData.*;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_2_ID;

public abstract class AbstractDishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @After
    public void tearDown() throws Exception {
        cacheManager.getCache("dishes").clear();
    }

    @Test
    public void testAdd() throws Exception {
        Dish toCreateDish = TestDishFactory.newInstanceForCreate();
        Integer restaurantId = toCreateDish.getRestaurant().getId();
        Dish created = service.add(toCreateDish, restaurantId);
        Assert.assertNotNull(toCreateDish.getId());
        MATCHER.assertEquals(toCreateDish, created);
        MATCHER.assertCollectionsEquals(Arrays.asList(R_1_DISH_1, R_1_DISH_2, R_1_DISH_3, R_1_DISH_4, created), service.findAll(restaurantId));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddDuplicateName() throws Exception {
        Dish toCreateDish = TestDishFactory.newInstanceForCreate();
        int restaurantId = toCreateDish.getRestaurant().getId();
        toCreateDish.setName(R_1_DISH_1.getName());
        service.add(toCreateDish, restaurantId);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddRestaurantNotFound() throws Exception {
        Dish toCreateDish = TestDishFactory.newInstanceForCreateForNonexistentRestaurant();
        Integer restaurantId = toCreateDish.getRestaurant().getId();
        service.add(toCreateDish, restaurantId);
    }

    @Test
    public void testFindById() throws Exception {
        Dish dish = service.findById(R_1_DISH_1_ID, RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_DISH_1, dish);
    }

    @Test(expected = DishNotFoundException.class)
    public void testFindByIdDishNotFound() throws Exception {
        service.findById(NOT_FOUND_INDEX, RESTAURANT_1_ID);
    }

    @Test(expected = DishNotFoundException.class)
    public void testFindByIdRestaurantNotFound() throws Exception {
        Assert.assertNotNull(service.findById(R_1_DISH_1_ID, RESTAURANT_1_ID));
        service.findById(R_1_DISH_1_ID, RestaurantTestData.NOT_FOUND_INDEX);
    }

    @Test(expected = DishNotFoundException.class)
    public void testFindByIdForeignDishShouldNotBeObtained() throws Exception {
        Assert.assertNotNull(service.findById(R_1_DISH_1_ID, RESTAURANT_1_ID));
        service.findById(R_1_DISH_1_ID, RESTAURANT_2_ID);
    }

    @Test
    public void testFindAll() throws Exception {
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, service.findAll(RESTAURANT_1_ID));
    }

    @Test
    public void testFindAllRestaurantNotFound() throws Exception {
        MATCHER.assertCollectionsEquals(Collections.emptyList(), service.findAll(RestaurantTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testUpdate() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdate();
        Integer restaurantId = toUpdateDish.getRestaurant().getId();
        service.update(toUpdateDish, restaurantId);
        MATCHER.assertCollectionsEquals(Arrays.asList(toUpdateDish, R_1_DISH_2, R_1_DISH_3, R_1_DISH_4), service.findAll(restaurantId));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUpdateDuplicateName() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdate();
        int restaurantId = toUpdateDish.getRestaurant().getId();
        toUpdateDish.setName(R_1_DISH_2.getName());
        service.update(toUpdateDish, restaurantId);
    }

    @Test(expected = DishNotFoundException.class)
    public void testUpdateDishNotFound() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateNonexistentDish();
        Integer restaurantId = toUpdateDish.getRestaurant().getId();
        service.update(toUpdateDish, restaurantId);
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, service.findAll(restaurantId));
    }

    @Test(expected = DishNotFoundException.class)
    public void testUpdateRestaurantNotFound() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateForNonexistentRestaurant();
        Integer restaurantId = toUpdateDish.getRestaurant().getId();
        service.update(toUpdateDish, restaurantId);
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, service.findAll(RESTAURANT_1_ID));
    }

    @Test(expected = DishNotFoundException.class)
    public void testUpdateForeignDishShouldNotBeUpdated() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateForeignDish();
        Integer restaurantId = toUpdateDish.getRestaurant().getId();
        service.update(toUpdateDish, restaurantId);
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, service.findAll(RESTAURANT_1_ID));
        MATCHER.assertCollectionsEquals(R_2_ALL_DISHES, service.findAll(RESTAURANT_2_ID));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(R_1_DISH_1_ID, RESTAURANT_1_ID);
        MATCHER.assertCollectionsEquals(R_1_AFTER_DELETE_DISHES, service.findAll(RESTAURANT_1_ID));
    }

    @Test(expected = DishNotFoundException.class)
    public void testDeleteDishNotFound() throws Exception {
        service.delete(NOT_FOUND_INDEX, RESTAURANT_1_ID);
    }

    @Test(expected = DishNotFoundException.class)
    public void testDeleteRestaurantNotFound() throws Exception {
        service.delete(R_1_DISH_1_ID, RestaurantTestData.NOT_FOUND_INDEX);
    }

    @Test
    public void testChangeInMenuState() throws Exception {
        boolean initialState = service.findById(R_1_DISH_1_ID, RESTAURANT_1_ID).isInMenu();
        service.changeInMenuState(R_1_DISH_1_ID, RESTAURANT_1_ID);
        Assert.assertNotEquals(initialState, service.findById(R_1_DISH_1_ID, RESTAURANT_1_ID).isInMenu());
    }
}
