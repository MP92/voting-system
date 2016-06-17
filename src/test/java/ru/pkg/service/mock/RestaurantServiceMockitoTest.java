package ru.pkg.service.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.DishRepository;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.service.RestaurantService;
import ru.pkg.utils.exception.RestaurantNotFoundException;


import static org.mockito.Mockito.*;
import static ru.pkg.testdata.RestaurantTestData.*;

public class RestaurantServiceMockitoTest extends AbstractServiceMockitoTest {

    @Autowired
    RestaurantService service;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Before
    public void setUp() throws Exception {
        reset(restaurantRepository);
        reset(dishRepository);
    }

    @Test
    public void testAdd() throws Exception {
        Restaurant toCreateRestaurant = TestRestaurantFactory.newIntanceForCreate();
        when(restaurantRepository.save(toCreateRestaurant)).thenAnswer(invocation -> {
            toCreateRestaurant.setId(NEW_RESTAURANT_ID);
            return toCreateRestaurant;
        });
        Restaurant created = service.add(toCreateRestaurant);
        verify(restaurantRepository).save(toCreateRestaurant);
        Assert.assertTrue(toCreateRestaurant.getId() == NEW_RESTAURANT_ID);
        MATCHER.assertEquals(toCreateRestaurant, created);
    }

    @Test
    public void testFindById() throws Exception {
        when(restaurantRepository.findById(START_INDEX)).thenReturn(RESTAURANT_1);
        MATCHER.assertEquals(RESTAURANT_1, service.findById(START_INDEX));
        verify(restaurantRepository).findById(START_INDEX);
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testFindByIdNotFound() throws Exception {
        when(restaurantRepository.findById(NOT_FOUND_INDEX)).thenReturn(null);
        try {
            service.findById(NOT_FOUND_INDEX);
        } catch (RestaurantNotFoundException e) {
            verify(restaurantRepository).findById(NOT_FOUND_INDEX);
            throw e;
        }
    }

    @Test
    public void testFindAll() throws Exception {
        when(restaurantRepository.findAll()).thenReturn(ALL_RESTAURANTS_WITHOUT_MENU);
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS_WITHOUT_MENU, service.findAll());
        verify(restaurantRepository).findAll();
    }

    @Test
    public void testFindAllWithMenu() throws Exception {
        when(restaurantRepository.findAll()).thenReturn(ALL_RESTAURANTS_WITHOUT_MENU);
        when(dishRepository.findInAllMenus()).thenReturn(ALL_MENUS);
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS_WITH_MENU, service.findAllWithMenu());
        verify(restaurantRepository).findAll();
        verify(dishRepository).findInAllMenus();
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant toUpdateRestaurant = TestRestaurantFactory.newIntanceForUpdate();
        when(restaurantRepository.save(toUpdateRestaurant)).thenReturn(toUpdateRestaurant);
        service.update(toUpdateRestaurant);
        verify(restaurantRepository).save(toUpdateRestaurant);
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        Restaurant toUpdateRestaurant = TestRestaurantFactory.newIntanceForUpdateNonexistent();
        when(restaurantRepository.save(toUpdateRestaurant)).thenReturn(null);
        try {
            service.update(toUpdateRestaurant);
        } catch (RestaurantNotFoundException e) {
            verify(restaurantRepository).save(toUpdateRestaurant);
            throw e;
        }
    }

    @Test
    public void testDelete() throws Exception {
        when(restaurantRepository.delete(START_INDEX)).thenReturn(true);
        service.delete(START_INDEX);
        verify(restaurantRepository).delete(START_INDEX);
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        when(restaurantRepository.delete(NOT_FOUND_INDEX)).thenReturn(false);
        try {
            service.delete(NOT_FOUND_INDEX);
        } catch (RestaurantNotFoundException e) {
            verify(restaurantRepository).delete(NOT_FOUND_INDEX);
            throw e;
        }
    }
}
