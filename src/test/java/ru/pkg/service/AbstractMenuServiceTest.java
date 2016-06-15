package ru.pkg.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.DishTestData;
import ru.pkg.RestaurantTestData;
import ru.pkg.model.Menu;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

import static ru.pkg.RestaurantTestData.RESTAURANT_1_ID;

import static ru.pkg.DishTestData.R_1_DISH_1_ID;
import static ru.pkg.DishTestData.R_1_DISH_4_ID;
import static ru.pkg.DishTestData.R_2_DISH_1_ID;

import static ru.pkg.MenuTestData.*;

public abstract class AbstractMenuServiceTest extends AbstractServiceTest {

    @Autowired
    MenuService service;

    @Test
    public void testAdd() throws Exception {
        service.add(R_1_DISH_4_ID, RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_AFTER_ADD_MENU, service.findById(RESTAURANT_1_ID));
    }

    @Test(expected = DishNotFoundException.class)
    public void testAddDishNotFound() throws Exception {
        service.add(R_2_DISH_1_ID, RESTAURANT_1_ID);
    }

    @Test
    public void testAddMultiple() throws Exception {
        service.delete(R_1_DISH_1_ID, RESTAURANT_1_ID);
        service.add(R_1_TO_PUT_MENU);
        MATCHER.assertEquals(R_1_AFTER_ADD_MENU, service.findById(RESTAURANT_1_ID));
    }

    @Test(expected = DishNotFoundException.class)
    public void testAddMultipleDishNotFound() throws Exception {
        service.add(R_1_DISH_NOT_FOUND_MENU);
    }

    @Test
    public void testReplace() throws Exception {
        service.replace(R_1_TO_PUT_MENU);
        MATCHER.assertEquals(R_1_AFTER_REPLACE_MENU, service.findById(RESTAURANT_1_ID));
    }

    @Test(expected = DishNotFoundException.class)
    public void testReplaceDishNotFound() throws Exception {
        service.replace(R_1_DISH_NOT_FOUND_MENU);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Menu> menus = service.findAll();
        MATCHER.assertCollectionsEquals(ALL_MENUS, menus);
    }

    @Test
    public void testFindById() throws Exception {
        Menu menu = service.findById(RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_MENU, menu);
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testFindByIdNotFound() throws Exception {
        service.findById(RestaurantTestData.NOT_FOUND_INDEX);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(R_1_DISH_1_ID, RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_AFTER_DELETE_MENU, service.findById(RESTAURANT_1_ID));
    }

    @Test(expected = DishNotFoundException.class)
    public void testDeleteRestaurantNotFound() throws Exception {
        service.delete(R_1_DISH_1_ID, RestaurantTestData.NOT_FOUND_INDEX);
    }

    @Test(expected = DishNotFoundException.class)
    public void testDeleteDishNotFound() throws Exception {
        service.delete(DishTestData.NOT_FOUND_INDEX, RESTAURANT_1_ID);
    }

    @Test
    public void testDeleteAll() throws Exception {
        service.deleteAll(RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_EMPTY_MENU, service.findById(RESTAURANT_1_ID));
    }
}
