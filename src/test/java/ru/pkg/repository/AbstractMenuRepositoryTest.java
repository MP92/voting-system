package ru.pkg.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.DishTestData;
import ru.pkg.RestaurantTestData;
import ru.pkg.model.Menu;

import java.util.Arrays;
import java.util.List;

import static ru.pkg.DishTestData.R_1_DISH_4_ID;
import static ru.pkg.DishTestData.R_1_DISH_1_ID;

import static ru.pkg.RestaurantTestData.RESTAURANT_1_ID;

import static ru.pkg.MenuTestData.*;

public abstract class AbstractMenuRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    MenuRepository repository;

    @Test
    public void testSave() throws Exception {
        repository.save(RESTAURANT_1_ID, R_1_DISH_4_ID);
        MATCHER.assertEquals(R_1_AFTER_ADD_MENU, repository.findById(RESTAURANT_1_ID));
    }

    @Test
    public void testSaveMultiple() throws Exception {
        repository.delete(RESTAURANT_1_ID, R_1_DISH_1_ID);
        repository.save(new Menu(RESTAURANT_1_ID, Arrays.asList(R_1_DISH_1_ID, R_1_DISH_4_ID)));
        MATCHER.assertEquals(R_1_AFTER_ADD_MENU, repository.findById(RESTAURANT_1_ID));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Menu> menus = repository.findAll();
        Assert.assertEquals(menus.size(), 2);
        MATCHER.assertCollectionsEquals(ALL_MENUS, menus);
    }

    @Test
    public void testFindById() throws Exception {
        Menu menu = repository.findById(RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_MENU, menu);
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        MATCHER.assertEquals(NOT_FOUND_MENU, repository.findById(RestaurantTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testDelete() throws Exception {
        Assert.assertTrue(repository.delete(RESTAURANT_1_ID, R_1_DISH_1_ID));
        MATCHER.assertEquals(R_1_AFTER_DELETE_MENU, repository.findById(RESTAURANT_1_ID));
    }

    @Test
    public void testDeleteRestaurantNotFound() throws Exception {
        Assert.assertFalse(repository.delete(RestaurantTestData.NOT_FOUND_INDEX, R_1_DISH_1_ID));
        MATCHER.assertEquals(R_1_MENU, repository.findById(RESTAURANT_1_ID));
    }

    @Test
    public void testDeleteDishNotFound() throws Exception {
        Assert.assertFalse(repository.delete(RESTAURANT_1_ID, DishTestData.NOT_FOUND_INDEX));
        MATCHER.assertEquals(R_1_MENU, repository.findById(RESTAURANT_1_ID));
    }

    @Test
    public void testDeleteAll() throws Exception {
        repository.deleteAll(RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_EMPTY_MENU, repository.findById(RESTAURANT_1_ID));
    }
}
