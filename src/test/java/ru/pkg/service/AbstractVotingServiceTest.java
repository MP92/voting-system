package ru.pkg.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.testdata.RestaurantTestData;
import ru.pkg.testdata.UserTestData;
import ru.pkg.model.UserVote;
import ru.pkg.utils.exception.RestaurantNotFoundException;
import ru.pkg.utils.exception.VotingException;

import java.util.Arrays;
import java.util.Collections;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.UserTestData.USER_1_ID;
import static ru.pkg.testdata.UserVoteTestData.*;
import static ru.pkg.testdata.UserVoteTestData.MATCHER;

public abstract class AbstractVotingServiceTest extends AbstractServiceTest {

    @Autowired
    VotingService service;

    @Test
    public void testUpdate() throws Exception {
        UserVote updatedVote = service.save(USER_1_ID, RESTAURANT_1_ID);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN_VOTE, updatedVote), service.findAll());
    }

    @Test(expected = VotingException.class)
    public void testUpdateUserNotFound() throws Exception {
        service.save(UserTestData.NOT_FOUND_INDEX, RESTAURANT_1_ID);
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, service.findAll());
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void testUpdateRestaurantNotFound() throws Exception {
        service.save(USER_1_ID, RestaurantTestData.NOT_FOUND_INDEX);
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, service.findAll());
    }

    @Test
    public void testFindById() throws Exception {
        MATCHER.assertEquals(USER_1_VOTE, service.findById(USER_1_ID));
    }

    @Test(expected = VotingException.class)
    public void testFindByIdUserNotFound() throws Exception {
        service.findById(UserTestData.NOT_FOUND_INDEX);
    }

    @Test
    public void testFindAll() throws Exception {
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, service.findAll());
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_1_ID);
        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTE), service.findAll());
    }

    @Test(expected = VotingException.class)
    public void testDeleteUserNotFound() throws Exception {
        service.delete(UserTestData.NOT_FOUND_INDEX);
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, service.findAll());
    }

    @Test
    public void testReset() throws Exception {
        service.reset();
        MATCHER.assertCollectionsEquals(Collections.emptyList(), service.findAll());
    }
}
