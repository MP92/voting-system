package ru.pkg.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.testdata.UserTestData;
import ru.pkg.model.UserVote;
import ru.pkg.utils.exception.VotingException;

import java.util.Collections;
import java.util.List;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.UserTestData.USER_1_ID;
import static ru.pkg.testdata.UserVoteTestData.*;
import static ru.pkg.testdata.UserVoteTestData.MATCHER;

public abstract class AbstractVotingServiceTest extends AbstractServiceTest {

    @Autowired
    VotingService service;

    @Test
    public void testSave() throws Exception {
        UserVote userVoteToUse = new UserVote(USER_1_ID, RESTAURANT_1_ID);
        service.save(userVoteToUse);
        UserVote userVote = service.findById(USER_1_ID);
        userVoteToUse.setLastVoted(userVote.getLastVoted());
        MATCHER.assertEquals(userVoteToUse, userVote);
    }

    @Test(expected = VotingException.class)
    public void testSaveUserNotFound() throws Exception {
        service.save(VOTE_USER_NOT_FOUND);
    }

    @Test(expected = VotingException.class)
    public void testSaveRestaurantNotFound() throws Exception {
        service.save(VOTE_RESTAURANT_NOT_FOUND);
    }

    @Test
    public void testFindById() throws Exception {
        UserVote userVote = service.findById(USER_1_ID);
        MATCHER.assertEquals(USER_1_VOTE, userVote);
    }

    @Test(expected = VotingException.class)
    public void testFindByIdUserNotFound() throws Exception {
        service.findById(UserTestData.NOT_FOUND_INDEX);
    }

    @Test
    public void testFindAll() throws Exception {
        List<UserVote> userVotes = service.findAll();
        MATCHER.assertCollectionsEquals(ALL_USER_VOTES, userVotes);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_1_ID);
        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTE), service.findAll());
    }

    @Test(expected = VotingException.class)
    public void testDeleteUserNotFound() throws Exception {
        service.delete(UserTestData.NOT_FOUND_INDEX);
    }

    @Test
    public void testReset() throws Exception {
        service.reset();
        MATCHER.assertCollectionsEquals(Collections.emptyList(), service.findAll());
    }
}
