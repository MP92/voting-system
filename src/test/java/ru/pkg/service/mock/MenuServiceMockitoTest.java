package ru.pkg.service.mock;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.testdata.DishTestData;
import ru.pkg.testdata.RestaurantTestData;
import ru.pkg.repository.MenuRepository;
import ru.pkg.service.MenuService;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import static org.mockito.Mockito.*;

import static ru.pkg.testdata.DishTestData.R_1_DISH_1_ID;
import static ru.pkg.testdata.DishTestData.R_1_DISH_4_ID;
import static ru.pkg.testdata.DishTestData.R_2_DISH_1_ID;

import static ru.pkg.testdata.MenuTestData.*;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;

public class MenuServiceMockitoTest extends AbstractServiceMockitoTest {

    @Autowired
    MenuService service;

    @Autowired
    MenuRepository repository;

    @Before
    public void setUp() throws Exception {
        reset(repository);
    }

    @Test
    public void testAdd() throws Exception {
        service.add(R_1_DISH_4_ID, RESTAURANT_1_ID);
        verify(repository).save(R_1_DISH_4_ID, RESTAURANT_1_ID);
    }

    @Test(expected = DishNotFoundException.class)
    public void testAddDishNotFound() throws Exception {
        doThrow(new DataIntegrityViolationException("")).when(repository).save(R_2_DISH_1_ID, RESTAURANT_1_ID);
        service.add(R_2_DISH_1_ID, RESTAURANT_1_ID);
    }

    @Test
    public void testAddMultiple() throws Exception {
        service.add(R_1_TO_PUT_MENU);
        verify(repository).save(R_1_TO_PUT_MENU);
    }

    @Test(expected = DishNotFoundException.class)
    public void testAddMultipleDishNotFound() throws Exception {
        doThrow(new DataIntegrityViolationException("")).when(repository).save(R_1_DISH_NOT_FOUND_MENU);
        service.add(R_1_DISH_NOT_FOUND_MENU);
    }

    @Test
    public void testReplace() throws Exception {
        service.replace(R_1_TO_PUT_MENU);
        verify(repository).save(R_1_TO_PUT_MENU);
    }

    @Test(expected = DishNotFoundException.class)
    public void testReplaceDishNotFound() throws Exception {
        doThrow(new DataIntegrityViolationException("")).when(repository).save(R_1_DISH_NOT_FOUND_MENU);
        service.replace(R_1_DISH_NOT_FOUND_MENU);
    }

    @Test
    public void testFindAll() throws Exception {
        when(repository.findAll()).thenReturn(ALL_MENUS);
        MATCHER.assertCollectionsEquals(ALL_MENUS, service.findAll());
        verify(repository).findAll();
    }

    @Test
    public void testFindById() throws Exception {
        when(repository.findById(RESTAURANT_1_ID)).thenReturn(R_1_MENU);
        MATCHER.assertEquals(R_1_MENU, service.findById(RESTAURANT_1_ID));
        verify(repository).findById(RESTAURANT_1_ID);
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testFindByIdNotFound() throws Exception {
        when(repository.findById(RestaurantTestData.NOT_FOUND_INDEX)).thenReturn(null);
        service.findById(RestaurantTestData.NOT_FOUND_INDEX);
    }

    @Test
    public void testDelete() throws Exception {
        when(repository.delete(R_1_DISH_1_ID, RESTAURANT_1_ID)).thenReturn(true);
        service.delete(R_1_DISH_1_ID, RESTAURANT_1_ID);
        verify(repository).delete(R_1_DISH_1_ID, RESTAURANT_1_ID);
    }

    @Test(expected = DishNotFoundException.class)
    public void testDeleteRestaurantNotFound() throws Exception {
        when(repository.delete(R_1_DISH_1_ID, RestaurantTestData.NOT_FOUND_INDEX)).thenReturn(false);
        service.delete(R_1_DISH_1_ID, RestaurantTestData.NOT_FOUND_INDEX);
    }

    @Test(expected = DishNotFoundException.class)
    public void testDeleteDishNotFound() throws Exception {
        when(repository.delete(DishTestData.NOT_FOUND_INDEX, RESTAURANT_1_ID)).thenReturn(false);
        service.delete(DishTestData.NOT_FOUND_INDEX, RESTAURANT_1_ID);
    }

    @Test
    public void testDeleteAll() throws Exception {
        service.deleteAll(RESTAURANT_1_ID);
        verify(repository).deleteAll(RESTAURANT_1_ID);
    }
}
