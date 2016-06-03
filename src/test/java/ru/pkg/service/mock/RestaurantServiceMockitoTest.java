package ru.pkg.service.mock;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.service.RestaurantService;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static ru.pkg.RestaurantTestData.*;

public class RestaurantServiceMockitoTest extends AbstractServiceMockitoTests {

    @Autowired
    RestaurantService service;

    @Autowired
    RestaurantRepository repository;

    @Test
    public void testFindById() throws Exception {
        when(repository.findById(START_INDEX)).thenReturn(TEST_RESTAURANT_1);
        MATCHER.assertEquals(TEST_RESTAURANT_1, service.findById(START_INDEX));
        verify(repository).findById(START_INDEX);
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testFindByIdNotFound() throws Exception {
        when(repository.findById(NOT_FOUND_INDEX)).thenReturn(null);
        service.findById(NOT_FOUND_INDEX);
    }

    @Test
    public void testDelete() throws Exception {
        when(repository.delete(START_INDEX)).thenReturn(true);
        service.delete(START_INDEX);
        verify(repository).delete(START_INDEX);
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        when(repository.delete(NOT_FOUND_INDEX)).thenReturn(false);
        service.delete(NOT_FOUND_INDEX);
        verify(repository).delete(NOT_FOUND_INDEX);
    }

    @Test
    public void testAdd() throws Exception {
        TestRestaurant toCreateRestaurant = new TestRestaurant(null, TEST_RESTAURANT_NEW);
        when(repository.save(toCreateRestaurant)).thenReturn(toCreateRestaurant);
        Restaurant created = service.add(toCreateRestaurant);
        verify(repository).save(toCreateRestaurant);
        MATCHER.assertEquals(toCreateRestaurant, created);
    }

    @Test
    public void testUpdate() throws Exception {
        TestRestaurant toUpdateRestaurant = new TestRestaurant(START_INDEX, TEST_RESTAURANT_NEW);
        when(repository.save(toUpdateRestaurant)).thenReturn(toUpdateRestaurant);
        service.update(toUpdateRestaurant);
        verify(repository).save(toUpdateRestaurant);
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        TestRestaurant toUpdateRestaurant = new TestRestaurant(NOT_FOUND_INDEX, TEST_RESTAURANT_NEW);
        when(repository.save(toUpdateRestaurant)).thenReturn(null);
        service.update(toUpdateRestaurant);
    }

    @Test
    public void testFindAll() throws Exception {
        when(repository.findAll()).thenReturn(Arrays.asList(TEST_RESTAURANT_1, TEST_RESTAURANT_2));
        MATCHER.assertCollectionsEquals(Arrays.asList(TEST_RESTAURANT_1, TEST_RESTAURANT_2), service.findAll());
        verify(repository).findAll();
    }
}
