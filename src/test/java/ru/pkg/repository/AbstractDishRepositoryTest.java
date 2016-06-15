package ru.pkg.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.RestaurantTestData;
import ru.pkg.model.Dish;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static ru.pkg.DishTestData.*;
import static ru.pkg.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.RestaurantTestData.R_1_IN_MENU_DISHES;
import static ru.pkg.RestaurantTestData.RESTAURANT_2_ID;
import static ru.pkg.RestaurantTestData.R_2_IN_MENU_DISHES;

public abstract class AbstractDishRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private DishRepository repository;

    @Test
    public void testAdd() throws Exception {
        Dish toCreateDish = TestDishFactory.newInstanceForCreate();
        Dish created = repository.save(toCreateDish);
        Assert.assertNotNull(toCreateDish.getId());
        MATCHER.assertEquals(toCreateDish, created);
        MATCHER.assertCollectionsEquals(Arrays.asList(R_1_DISH_1, R_1_DISH_2, R_1_DISH_3, R_1_DISH_4, created), repository.findAll(RESTAURANT_1_ID));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddRestaurantNotFound() throws Exception {
        Dish toCreateDish = TestDishFactory.newInstanceForCreateForNonexistentRestaurant();
        repository.save(toCreateDish);
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
        Dish toUpdate = TestDishFactory.newInstanceForUpdate();
        Dish updated = repository.save(toUpdate);
        Assert.assertTrue(updated.getId() == R_1_DISH_1_ID);
        MATCHER.assertEquals(toUpdate, updated);
        MATCHER.assertCollectionsEquals(Arrays.asList(TestDishFactory.newInstanceForUpdate(), R_1_DISH_2, R_1_DISH_3, R_1_DISH_4), repository.findAll(RESTAURANT_1_ID));
    }

    @Test
    public void testUpdateDishNotFound() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateNonexistentDish();
        Assert.assertNull(repository.save(toUpdateDish));
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, repository.findAll(RESTAURANT_1_ID));
    }

    @Test
    public void testUpdateRestaurantNotFound() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateForNonexistentRestaurant();
        Assert.assertNull(repository.save(toUpdateDish));
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, repository.findAll(RESTAURANT_1_ID));
    }

    @Test
    public void testUpdateForeignDishShouldNotBeUpdated() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateForeignDish();
        Assert.assertNull(repository.save(toUpdateDish));
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

    @Test
    public void testFindInAllMenus() throws Exception {
        Map<Integer, List<Dish>> menus = repository.findInAllMenus();
        Assert.assertEquals(menus.size(), 2);
        MATCHER.assertCollectionsEquals(R_1_IN_MENU_DISHES, menus.get(RESTAURANT_1_ID));
        MATCHER.assertCollectionsEquals(R_2_IN_MENU_DISHES, menus.get(RESTAURANT_2_ID));
    }

    @Test
    public void testFindInMenu() throws Exception {
        List<Dish> menu = repository.findInMenu(RESTAURANT_1_ID);
        MATCHER.assertCollectionsEquals(R_1_IN_MENU_DISHES, menu);
    }

    @Test
    public void testFindInMenuRestaurantNotFound() throws Exception {
        MATCHER.assertCollectionsEquals(Collections.emptyList(), repository.findInMenu(RestaurantTestData.NOT_FOUND_INDEX));
    }
}
