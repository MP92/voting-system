package ru.pkg.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Restaurant;

import java.util.Arrays;
import java.util.Collections;

import static ru.pkg.testdata.RestaurantTestData.*;

public abstract class AbstractRestaurantRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private RestaurantRepository repository;

    @Test
    public void testAdd() throws Exception {
        Restaurant toCreateRestaurant = TestRestaurantFactory.newInstanceForCreate();
        Restaurant created = repository.save(toCreateRestaurant);
        Assert.assertNotNull(toCreateRestaurant.getId());
        MATCHER.assertEquals(toCreateRestaurant, created);
        MATCHER.assertCollectionsEquals(Arrays.asList(RESTAURANT_1, RESTAURANT_2, created), repository.findAll());
    }

    @Test
    public void testFindById() throws Exception {
        MATCHER.assertEquals(RESTAURANT_1, repository.findById(RESTAURANT_1_ID));
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        Assert.assertNull(repository.findById(NOT_FOUND_INDEX));
    }

    @Test
    public void testFindAll() throws Exception {
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS, repository.findAll());
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant toUpdateRestaurant = TestRestaurantFactory.newInstanceForUpdate();
        Restaurant updated = repository.save(toUpdateRestaurant);
        Assert.assertTrue(updated.getId() == RESTAURANT_1_ID);
        MATCHER.assertEquals(toUpdateRestaurant, updated);
        MATCHER.assertCollectionsEquals(Arrays.asList(RESTAURANT_2, updated), repository.findAll());
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Restaurant toUpdateRestaurant = TestRestaurantFactory.newInstanceForUpdateNonexistent();
        Assert.assertNull(repository.save(toUpdateRestaurant));
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS, repository.findAll());
    }

    @Test
    public void testDelete() throws Exception {
        Assert.assertTrue(repository.delete(RESTAURANT_1_ID));
        MATCHER.assertCollectionsEquals(Collections.singletonList(RESTAURANT_2), repository.findAll());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        Assert.assertFalse(repository.delete(NOT_FOUND_INDEX));
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS, repository.findAll());
    }
}
