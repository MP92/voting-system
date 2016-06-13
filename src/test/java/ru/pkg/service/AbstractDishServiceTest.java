package ru.pkg.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.RestaurantTestData;
import ru.pkg.model.Dish;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.pkg.DishTestData.*;
import static ru.pkg.DishTestData.MATCHER;
import static ru.pkg.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.RestaurantTestData.RESTAURANT_1_MENU;
import static ru.pkg.RestaurantTestData.RESTAURANT_2_ID;

public abstract class AbstractDishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    public void testAdd() throws Exception {
        Dish toCreateDish = TestDishFactory.newInstanceForCreate();
        Dish created = service.add(toCreateDish);
        Assert.assertNotNull(toCreateDish.getId());
        MATCHER.assertEquals(toCreateDish, created);
        MATCHER.assertCollectionsEquals(Arrays.asList(R_1_DISH_1, R_1_DISH_2, R_1_DISH_3, R_1_DISH_4, created), service.findAll(RESTAURANT_1_ID));
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testAddRestaurantNotFound() throws Exception {
        Dish toCreateDish = TestDishFactory.newInstanceForCreateForNonexistentRestaurant();
        service.add(toCreateDish);
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
        service.update(toUpdateDish);
        MATCHER.assertCollectionsEquals(Arrays.asList(TestDishFactory.newInstanceForUpdate(), R_1_DISH_2, R_1_DISH_3, R_1_DISH_4), service.findAll(RESTAURANT_1_ID));
    }

    @Test(expected = DishNotFoundException.class)
    public void testUpdateDishNotFound() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateNonexistentDish();
        service.update(toUpdateDish);
    }

    @Test(expected = DishNotFoundException.class)
    public void testUpdateRestaurantNotFound() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateForNonexistentRestaurant();
        service.update(toUpdateDish);
    }

    @Test(expected = DishNotFoundException.class)
    public void testUpdateForeignDishShouldNotBeUpdated() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateForeignDish();
        service.update(toUpdateDish);
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

/*    @Test
    public void testFindMenu() throws Exception {
        MATCHER.assertCollectionsEquals(RESTAURANT_1_MENU, service.findMenu(RESTAURANT_1_ID));
    }

    @Test
    public void testFindMenuRestaurantNotFound() throws Exception {
        MATCHER.assertCollectionsEquals(Collections.emptyList(), service.findMenu(RestaurantTestData.NOT_FOUND_INDEX));
    }*/
}
