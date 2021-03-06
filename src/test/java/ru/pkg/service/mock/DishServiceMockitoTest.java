package ru.pkg.service.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.testdata.RestaurantTestData;
import ru.pkg.model.Dish;
import ru.pkg.repository.DishRepository;
import ru.pkg.service.DishService;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.Collections;

import static org.mockito.Mockito.*;

import static ru.pkg.testdata.DishTestData.*;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;

public class DishServiceMockitoTest extends AbstractServiceMockitoTest {

    @Autowired
    private DishService service;

    @Autowired
    private DishRepository repository;

    @Before
    public void setUp() throws Exception {
        reset(repository);
    }

    @Test
    public void testAdd() throws Exception {
        Dish toCreateDish = TestDishFactory.newInstanceForCreate();
        Integer restaurantId = toCreateDish.getRestaurant().getId();

        when(repository.save(toCreateDish, restaurantId)).thenAnswer(invocation -> {
            toCreateDish.setId(11111);
            return toCreateDish;
        });
        Dish created = service.add(toCreateDish, restaurantId);
        verify(repository).save(toCreateDish, restaurantId);
        Assert.assertNotNull(toCreateDish.getId());
        MATCHER.assertEquals(toCreateDish, created);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddRestaurantNotFound() throws Exception {
        Dish toCreateDish = TestDishFactory.newInstanceForCreateForNonexistentRestaurant();
        Integer restaurantId = toCreateDish.getRestaurant().getId();

        DataIntegrityViolationException exception = new DataIntegrityViolationException("");
        when(repository.save(toCreateDish, restaurantId)).thenThrow(exception);
        try {
            service.add(toCreateDish, restaurantId);
        } catch (RestaurantNotFoundException e) {
            verify(repository).save(toCreateDish, restaurantId);
            Assert.assertEquals(e.getCause(), exception);
            throw e;
        }
    }

    @Test
    public void testFindById() throws Exception {
        when(repository.findById(R_1_DISH_1_ID, RESTAURANT_1_ID)).thenReturn(R_1_DISH_1);
        Dish dish = service.findById(R_1_DISH_1_ID, RESTAURANT_1_ID);
        verify(repository).findById(R_1_DISH_1_ID, RESTAURANT_1_ID);
        MATCHER.assertEquals(R_1_DISH_1, dish);
    }

    @Test(expected = DishNotFoundException.class)
    public void testFindByIdDishNotFound() throws Exception {
        when(repository.findById(NOT_FOUND_INDEX, RESTAURANT_1_ID)).thenReturn(null);
        try {
            service.findById(NOT_FOUND_INDEX, RESTAURANT_1_ID);
        } catch (DishNotFoundException e) {
            verify(repository).findById(NOT_FOUND_INDEX, RESTAURANT_1_ID);
            throw e;
        }
    }

    @Test
    public void testFindAll() throws Exception {
        when(repository.findAll(RESTAURANT_1_ID)).thenReturn(R_1_ALL_DISHES);
        MATCHER.assertCollectionsEquals(R_1_ALL_DISHES, service.findAll(RESTAURANT_1_ID));
        verify(repository).findAll(RESTAURANT_1_ID);
    }

    @Test
    public void testFindAllRestaurantNotFound() throws Exception {
        when(repository.findAll(NOT_FOUND_INDEX)).thenReturn(Collections.emptyList());
        MATCHER.assertCollectionsEquals(Collections.emptyList(), service.findAll(RestaurantTestData.NOT_FOUND_INDEX));
        verify(repository).findAll(NOT_FOUND_INDEX);
    }

    @Test
    public void testUpdate() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdate();
        Integer restaurantId = toUpdateDish.getRestaurant().getId();

        when(repository.save(toUpdateDish, restaurantId)).thenReturn(toUpdateDish);
        service.update(toUpdateDish, restaurantId);
        verify(repository).save(toUpdateDish, restaurantId);
    }

    @Test(expected = DishNotFoundException.class)
    public void testUpdateDishNotFound() throws Exception {
        Dish toUpdateDish = TestDishFactory.newInstanceForUpdateNonexistentDish();
        Integer restaurantId = toUpdateDish.getRestaurant().getId();

        when(repository.save(toUpdateDish, restaurantId)).thenReturn(null);
        try {
            service.update(toUpdateDish, restaurantId);
        } catch (DishNotFoundException e) {
            verify(repository).save(toUpdateDish, restaurantId);
            throw e;
        }
    }

    @Test
    public void testDelete() throws Exception {
        when(repository.delete(R_1_DISH_1_ID, RESTAURANT_1_ID)).thenReturn(true);
        service.delete(R_1_DISH_1_ID, RESTAURANT_1_ID);
        verify(repository).delete(R_1_DISH_1_ID, RESTAURANT_1_ID);
    }

    @Test(expected = DishNotFoundException.class)
    public void testDeleteDishNotFound() throws Exception {
        when(repository.delete(NOT_FOUND_INDEX, RESTAURANT_1_ID)).thenReturn(false);
        try {
            service.delete(NOT_FOUND_INDEX, RESTAURANT_1_ID);
        } catch (DishNotFoundException e) {
            verify(repository).delete(NOT_FOUND_INDEX, RESTAURANT_1_ID);
            throw e;
        }
    }
}
