package ru.pkg.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.testdata.RestaurantTestData;
import ru.pkg.model.Dish;

import java.util.Arrays;
import java.util.Collections;

import static ru.pkg.testdata.DishTestData.*;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_2_ID;

public abstract class AbstractDishRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private DishRepository repository;

    @Test
    public void testAdd() throws Exception {
        Dish toCreateDish = TestDishFactory.newInstanceForCreate();
        int restaurantId = toCreateDish.getRestaurant().getId();
        Dish created = repository.save(toCreateDish, restaurantId);
        Assert.assertNotNull(toCreateDish.getId());
        MATCHER.assertEquals(toCreateDish, created);
        MATCHER.assertCollectionsEquals(Arrays.asList(R_1_DISH_1, R_1_DISH_2, R_1_DISH_3, R_1_DISH_4, created), repository.findAll(restaurantId));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddRestaurantNotFound() throws Exception {
        Dish toCreateDish = TestDishFactory.newInstanceForCreateForNonexistentRestaurant();
        repository.save(toCreateDish, toCreateDish.getRestaurant().getId());
    }

    @Test
    public void testFindById() throws Exception {
        Dish dish = repository.findById(R_1_DISH_1_ID, RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_DISH_1, dish);
    }

    @Test
    public void testFindByIdDishNotFound() throws Exception {
         Assert.assertNull(repository.findById(NOT_FOUND_INDEX, RESTAURANT_1_ID));
    }

    @Test
    public void testFindByIdRestaurantNotFound() throws Exception {
        Assert.assertNotNull(repository.findById(R_1_DISH_1_ID, RESTAURANT_1_ID));
        Assert.assertNull(repository.findById(R_1_DISH_1_ID, RestaurantTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testFindByIdForeignDishShouldNotBeObtained() throws Exception {
        Assert.assertNotNull(repository.findById(R_1_DISH_1_ID, RESTAURANT_1_ID));
        Assert.assertNull(repository.findById(R_1_DISH_1_ID, RESTAURANT_2_ID));
    }

    @Test
    public void testFindAll() throws Exception {
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, repository.findAll(RESTAURANT_1_ID));
    }

    @Test
    public void testFindAllRestaurantNotFound() throws Exception {
        MATCHER.assertCollectionsEquals(Collections.emptyList(), repository.findAll(RestaurantTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testUpdate() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdate();
        int restaurantId = toUpdateDish.getRestaurant().getId();
        Dish updated = repository.save(toUpdateDish, restaurantId);
        Assert.assertTrue(updated.getId() == R_1_DISH_1_ID);
        MATCHER.assertEquals(toUpdateDish, updated);
        MATCHER.assertCollectionsEquals(Arrays.asList(TestDishFactory.newInstanceForUpdate(), R_1_DISH_2, R_1_DISH_3, R_1_DISH_4), repository.findAll(restaurantId));
    }

    @Test
    public void testUpdateDishNotFound() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateNonexistentDish();
        int restaurantId = toUpdateDish.getRestaurant().getId();
        Assert.assertNull(repository.save(toUpdateDish, restaurantId));
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, repository.findAll(restaurantId));
    }

    @Test
    public void testUpdateRestaurantNotFound() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateForNonexistentRestaurant();
        int restaurantId = toUpdateDish.getRestaurant().getId();
        Assert.assertNull(repository.save(toUpdateDish, restaurantId));
    }

    @Test
    public void testUpdateForeignDishShouldNotBeUpdated() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateForeignDish();
        int restaurantId = toUpdateDish.getRestaurant().getId();
        Assert.assertNull(repository.save(toUpdateDish, restaurantId));
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, repository.findAll(RESTAURANT_1_ID));
        MATCHER.assertCollectionsEquals(R_2_ALL_DISHES, repository.findAll(RESTAURANT_2_ID));
    }

    @Test
    public void testDelete() throws Exception {
        Assert.assertTrue(repository.delete(R_1_DISH_1_ID, RESTAURANT_1_ID));
        MATCHER.assertCollectionsEquals(R_1_AFTER_DELETE_DISHES, repository.findAll(RESTAURANT_1_ID));
    }

    @Test
    public void testDeleteDishNotFound() throws Exception {
        Assert.assertFalse(repository.delete(NOT_FOUND_INDEX, RESTAURANT_1_ID));
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, repository.findAll(RESTAURANT_1_ID));
    }

    @Test
    public void testDeleteRestaurantNotFound() throws Exception {
        Assert.assertFalse(repository.delete(R_1_DISH_1_ID, RestaurantTestData.NOT_FOUND_INDEX));
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, repository.findAll(RESTAURANT_1_ID));
    }
}
