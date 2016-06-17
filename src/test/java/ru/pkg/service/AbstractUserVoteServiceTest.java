package ru.pkg.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.testdata.UserTestData;
import ru.pkg.model.Restaurant;
import ru.pkg.model.UserVote;
import ru.pkg.utils.exception.UserNotFoundException;
import ru.pkg.utils.exception.VotingException;

import java.util.Collections;
import java.util.List;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_2_ID;
import static ru.pkg.testdata.UserTestData.USER_1_ID;
import static ru.pkg.testdata.UserVoteTestData.*;
import static ru.pkg.testdata.UserVoteTestData.MATCHER;
import static ru.pkg.testdata.UserVoteTestData.RESTAURANT_1_VOTES_COUNT;

public abstract class AbstractUserVoteServiceTest extends AbstractServiceTest {

    @Autowired
    UserVoteService userVoteService;

    @Autowired
    RestaurantService restaurantService;

    @Test
    public void testSave() throws Exception {
        UserVote userVoteToUse = new UserVote(USER_1_ID, RESTAURANT_1_ID);
        userVoteService.save(userVoteToUse);
        UserVote userVote = userVoteService.findById(USER_1_ID);
        userVoteToUse.setDateTime(userVote.getDateTime());
        MATCHER.assertEquals(userVoteToUse, userVote);

        Restaurant restaurant = restaurantService.findById(RESTAURANT_1_ID);
        Assert.assertEquals(RESTAURANT_1_VOTES_COUNT + 1, restaurant.getVotes());
    }

    @Test
    public void testSaveUserNeverVoted() throws Exception {
        UserVote userVoteToUse = new UserVote(NOT_VOTED_USER_ID, RESTAURANT_1_ID);
        userVoteService.save(userVoteToUse);
        UserVote userVote = userVoteService.findById(NOT_VOTED_USER_ID);
        userVoteToUse.setDateTime(userVote.getDateTime());
        MATCHER.assertEquals(userVoteToUse, userVote);

        Restaurant restaurant = restaurantService.findById(RESTAURANT_1_ID);
        Assert.assertEquals(RESTAURANT_1_VOTES_COUNT + 1, restaurant.getVotes());
    }

    @Test(expected = VotingException.class)
    public void testShouldSaveOnlyOnceAtDay() throws Exception {
        UserVote userVoteToUse = new UserVote(USER_1_ID, RESTAURANT_1_ID);
        userVoteService.save(userVoteToUse);
        userVoteService.save(userVoteToUse);
    }

    @Test(expected = VotingException.class)
    public void testSaveUserNotFound() throws Exception {
        userVoteService.save(VOTE_USER_NOT_FOUND);
    }

    @Test(expected = VotingException.class)
    public void testSaveRestaurantNotFound() throws Exception {
        userVoteService.save(VOTE_RESTAURANT_NOT_FOUND);
    }

    @Test
    public void testFindById() throws Exception {
        UserVote userVote = userVoteService.findById(USER_1_ID);
        MATCHER.assertEquals(USER_1_VOTE, userVote);
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        MATCHER.assertEquals(EMPTY_VOTE, userVoteService.findById(UserTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testFindAll() throws Exception {
        List<UserVote> userVotes = userVoteService.findAll();
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS_VOTES, userVotes);
    }

    @Test
    public void testDelete() throws Exception {
        userVoteService.delete(USER_1_ID);
        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTE), userVoteService.findAll());

        Restaurant restaurant = restaurantService.findById(RESTAURANT_2_ID);
        Assert.assertEquals(RESTAURANT_1_VOTES_COUNT - 1, restaurant.getVotes());
    }

    @Test(expected = VotingException.class)
    public void testDeleteNotFound() throws Exception {
        userVoteService.delete(UserTestData.NOT_FOUND_INDEX);
    }

    @Test(expected = VotingException.class)
    public void testShouldDeleteOnceOnly() throws Exception {
        userVoteService.delete(USER_1_ID);

        try {
            userVoteService.delete(USER_1_ID);
        } catch (UserNotFoundException e) {
            Restaurant restaurant = restaurantService.findById(RESTAURANT_2_ID);
            Assert.assertEquals(RESTAURANT_1_VOTES_COUNT - 1, restaurant.getVotes());
            throw e;
        }
    }

    @Test
    public void testReset() throws Exception {
        userVoteService.reset();
        MATCHER.assertCollectionsEquals(Collections.emptyList(), userVoteService.findAll());

        List<Restaurant> restaurants = restaurantService.findAll();
        for (Restaurant restaurant : restaurants) {
            Assert.assertEquals(0, restaurant.getVotes());
        }
    }
}
