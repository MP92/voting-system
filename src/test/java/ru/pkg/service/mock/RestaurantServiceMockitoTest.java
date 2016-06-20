package ru.pkg.service.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.service.RestaurantService;
import ru.pkg.utils.exception.RestaurantNotFoundException;


import static org.mockito.Mockito.*;
import static ru.pkg.testdata.RestaurantTestData.*;
import static ru.pkg.testdata.RestaurantTestData.TestRestaurantFactory.*;

public class RestaurantServiceMockitoTest extends AbstractServiceMockitoTest {

    @Autowired
    RestaurantService service;

    @Autowired
    RestaurantRepository repository;

    @Before
    public void setUp() throws Exception {
        reset(repository);
    }

    @Test
    public void testAdd() throws Exception {
        Restaurant toCreateRestaurant = TestRestaurantFactory.newInstanceForCreate();
        when(repository.save(toCreateRestaurant)).thenAnswer(invocation -> {
            toCreateRestaurant.setId(NEW_RESTAURANT_ID);
            return toCreateRestaurant;
        });
        Restaurant created = service.add(toCreateRestaurant);
        verify(repository).save(toCreateRestaurant);
        Assert.assertTrue(toCreateRestaurant.getId() == NEW_RESTAURANT_ID);
        MATCHER.assertEquals(toCreateRestaurant, created);
    }

    @Test
    public void testFindById() throws Exception {
        when(repository.findById(START_INDEX)).thenReturn(copy(RESTAURANT_1));
        MATCHER.assertEquals(RESTAURANT_1, service.findById(START_INDEX));
        verify(repository).findById(START_INDEX);
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testFindByIdNotFound() throws Exception {
        when(repository.findById(NOT_FOUND_INDEX)).thenReturn(null);
        try {
            service.findById(NOT_FOUND_INDEX);
        } catch (RestaurantNotFoundException e) {
            verify(repository).findById(NOT_FOUND_INDEX);
            throw e;
        }
    }

    @Test
    public void testFindAll() throws Exception {
        when(repository.findAll()).thenReturn(ALL_RESTAURANTS);
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS, service.findAll());
        verify(repository).findAll();
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant toUpdateRestaurant = TestRestaurantFactory.newInstanceForUpdate();
        when(repository.save(toUpdateRestaurant)).thenReturn(toUpdateRestaurant);
        service.update(toUpdateRestaurant);
        verify(repository).save(toUpdateRestaurant);
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        Restaurant toUpdateRestaurant = TestRestaurantFactory.newInstanceForUpdateNonexistent();
        when(repository.save(toUpdateRestaurant)).thenReturn(null);
        try {
            service.update(toUpdateRestaurant);
        } catch (RestaurantNotFoundException e) {
            verify(repository).save(toUpdateRestaurant);
            throw e;
        }
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
        try {
            service.delete(NOT_FOUND_INDEX);
        } catch (RestaurantNotFoundException e) {
            verify(repository).delete(NOT_FOUND_INDEX);
            throw e;
        }
    }
}
