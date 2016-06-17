package ru.pkg.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.testdata.DishTestData;
import ru.pkg.testdata.RestaurantTestData;
import ru.pkg.model.Menu;

import java.util.List;

import static ru.pkg.testdata.DishTestData.R_1_DISH_4_ID;
import static ru.pkg.testdata.DishTestData.R_1_DISH_1_ID;

import static ru.pkg.testdata.DishTestData.R_2_DISH_1_ID;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;

import static ru.pkg.testdata.MenuTestData.*;

public abstract class AbstractMenuRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    MenuRepository repository;

    @Test
    public void testSave() throws Exception {
        repository.save(R_1_DISH_4_ID, RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_AFTER_ADD_MENU, repository.findById(RESTAURANT_1_ID));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveForeignDishIdShouldNotBeAdded() throws Exception {
        repository.save(R_2_DISH_1_ID, RESTAURANT_1_ID);
    }

    @Test
    public void testSaveMultiple() throws Exception {
        repository.delete(R_1_DISH_1_ID, RESTAURANT_1_ID);
        repository.save(R_1_TO_PUT_MENU);
        MATCHER.assertEquals(R_1_AFTER_ADD_MENU, repository.findById(RESTAURANT_1_ID));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveMultipleForeignDishIdShouldNotBeAdded() throws Exception {
        repository.save(R_1_DISH_NOT_FOUND_MENU);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Menu> menus = repository.findAll();
        MATCHER.assertCollectionsEquals(ALL_MENUS, menus);
    }

    @Test
    public void testFindById() throws Exception {
        Menu menu = repository.findById(RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_MENU, menu);
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        Assert.assertNull(repository.findById(RestaurantTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testDelete() throws Exception {
        Assert.assertTrue(repository.delete(R_1_DISH_1_ID, RESTAURANT_1_ID));
        MATCHER.assertEquals(R_1_AFTER_DELETE_MENU, repository.findById(RESTAURANT_1_ID));
    }

    @Test
    public void testDeleteRestaurantNotFound() throws Exception {
        Assert.assertFalse(repository.delete(R_1_DISH_1_ID, RestaurantTestData.NOT_FOUND_INDEX));
        MATCHER.assertEquals(R_1_MENU, repository.findById(RESTAURANT_1_ID));
    }

    @Test
    public void testDeleteDishNotFound() throws Exception {
        Assert.assertFalse(repository.delete(DishTestData.NOT_FOUND_INDEX, RESTAURANT_1_ID));
        MATCHER.assertEquals(R_1_MENU, repository.findById(RESTAURANT_1_ID));
    }

    @Test
    public void testDeleteAll() throws Exception {
        repository.deleteAll(RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_EMPTY_MENU, repository.findById(RESTAURANT_1_ID));
    }
}
