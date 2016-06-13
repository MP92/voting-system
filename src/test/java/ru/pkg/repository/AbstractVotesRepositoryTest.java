package ru.pkg.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.RestaurantTestData;

import static ru.pkg.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.VotesTestData.*;

public abstract class AbstractVotesRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    VotesRepository repository;

    @Test
    public void testUp() throws Exception {
        repository.up(RESTAURANT_1_ID);
        Assert.assertEquals(R_1_VOTES_COUNT + 1, repository.findCount(RESTAURANT_1_ID).intValue());
    }

    @Test
    public void testDown() throws Exception {
        repository.down(RESTAURANT_1_ID);
        Assert.assertEquals(R_1_VOTES_COUNT - 1, repository.findCount(RESTAURANT_1_ID).intValue());
    }

    @Test
    public void testFindById() throws Exception {
        MATCHER.assertEquals(RESTAURANT_1_VOTES, repository.findById(RESTAURANT_1_ID));
    }

    @Test
    public void testFindByIdRestaurantNotFound() throws Exception {
        Assert.assertNull(repository.findById(RestaurantTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testFindCount() throws Exception {
        Assert.assertEquals(R_1_VOTES_COUNT, repository.findCount(RESTAURANT_1_ID).intValue());
    }

    @Test
    public void testFindCountRestaurantNotFound() throws Exception {
        Assert.assertNull(repository.findCount(RestaurantTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testFindAll() throws Exception {
        MATCHER.assertCollectionsEquals(ALL_VOTES, repository.findAll());
    }

    @Test
    public void testReset() throws Exception {
        repository.reset();
        MATCHER.assertCollectionsEquals(AFTER_RESET_ALL_VOTES, repository.findAll());
    }
}
