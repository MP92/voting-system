package ru.pkg.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.transaction.TestTransaction;
import ru.pkg.testdata.RestaurantTestData;
import ru.pkg.testdata.UserTestData;
import ru.pkg.model.UserVote;

import java.util.Arrays;
import java.util.Collections;

import static ru.pkg.testdata.UserVoteTestData.*;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.UserTestData.USER_1_ID;

public abstract class AbstractVotingRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private VotingRepository repository;

    @Test
    public void testUpdate() throws Exception {
        UserVote updatedVote = repository.save(USER_1_ID, RESTAURANT_1_ID);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN_VOTE, updatedVote), repository.findAll());
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        Assert.assertNull(repository.save(UserTestData.NOT_FOUND_INDEX, RESTAURANT_1_ID));
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, repository.findAll());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUpdateRestaurantNotFound() throws Exception {
        repository.save(USER_1_ID, RestaurantTestData.NOT_FOUND_INDEX);
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, repository.findAll());

        TestTransaction.flagForCommit();
        TestTransaction.end();
    }

    @Test
    public void testFindById() throws Exception {
        MATCHER.assertEquals(USER_1_VOTE, repository.findById(USER_1_ID));
    }

    @Test
    public void testFindByIdUserNotFound() throws Exception {
        Assert.assertNull(repository.findById(UserTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testFindAll() throws Exception {
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, repository.findAll());
    }

    @Test
    public void testDelete() throws Exception {
        Assert.assertTrue(repository.delete(USER_1_ID));
        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTE), repository.findAll());
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        Assert.assertFalse(repository.delete(UserTestData.NOT_FOUND_INDEX));
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, repository.findAll());
    }

    @Test
    public void testReset() throws Exception {
        repository.reset();
        MATCHER.assertCollectionsEquals(Collections.emptyList(), repository.findAll());
    }
}
