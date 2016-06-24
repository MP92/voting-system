package ru.pkg.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.testdata.UserTestData;
import ru.pkg.model.UserVote;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.pkg.testdata.UserVoteTestData.*;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.UserTestData.USER_1_ID;

public abstract class AbstractVotingRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    VotingRepository repository;

    @Test
    public void testUpdate() throws Exception {
        UserVote userVoteToUse = new UserVote(USER_1_ID, RESTAURANT_1_ID);
        repository.save(userVoteToUse);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN_VOTE, userVoteToUse), repository.findAll());
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        Assert.assertNull(repository.save(VOTE_USER_NOT_FOUND));
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, repository.findAll());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUpdateRestaurantNotFound() throws Exception {
        repository.save(VOTE_RESTAURANT_NOT_FOUND);
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, repository.findAll());
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
