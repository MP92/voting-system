package ru.pkg.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.UserTestData;
import ru.pkg.model.Restaurant;
import ru.pkg.model.UserVote;

import java.util.Collections;
import java.util.List;

import static ru.pkg.UserVoteTestData.*;

import static ru.pkg.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.RestaurantTestData.RESTAURANT_2_ID;

import static ru.pkg.UserTestData.USER_1_ID;

public abstract class AbstractUserVoteRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    UserVoteRepository userVoteRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    public void testSave() throws Exception {
        UserVote userVoteToUse = new UserVote(USER_1_ID, RESTAURANT_1_ID);
        userVoteRepository.save(userVoteToUse);
        UserVote userVote = userVoteRepository.findById(USER_1_ID);
        userVoteToUse.setDateTime(userVote.getDateTime());
        MATCHER.assertEquals(userVoteToUse, userVote);

        Restaurant restaurant = restaurantRepository.findById(RESTAURANT_1_ID);
        Assert.assertEquals(RESTAURANT_1_VOTES_COUNT + 1, restaurant.getVotes());
    }

    @Test
    public void testSaveUserNeverVoted() throws Exception {
        UserVote userVoteToUse = new UserVote(NOT_VOTED_USER_ID, RESTAURANT_1_ID);
        userVoteRepository.save(userVoteToUse);
        UserVote userVote = userVoteRepository.findById(NOT_VOTED_USER_ID);
        userVoteToUse.setDateTime(userVote.getDateTime());
        MATCHER.assertEquals(userVoteToUse, userVote);

        Restaurant restaurant = restaurantRepository.findById(RESTAURANT_1_ID);
        Assert.assertEquals(RESTAURANT_1_VOTES_COUNT + 1, restaurant.getVotes());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testShouldSaveOnlyOnceAtDay() throws Exception {
        UserVote userVoteToUse = new UserVote(USER_1_ID, RESTAURANT_1_ID);
        userVoteRepository.save(userVoteToUse);

        try {
            userVoteRepository.save(userVoteToUse);
        } catch (DataIntegrityViolationException e) {
            Restaurant restaurant = restaurantRepository.findById(RESTAURANT_1_ID);
            Assert.assertEquals(RESTAURANT_1_VOTES_COUNT + 1, restaurant.getVotes());
            throw e;
        }
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveUserNotFound() throws Exception {
        userVoteRepository.save(VOTE_USER_NOT_FOUND);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveRestaurantNotFound() throws Exception {
        userVoteRepository.save(VOTE_RESTAURANT_NOT_FOUND);
    }

    @Test
    public void testFindById() throws Exception {
        UserVote userVote = userVoteRepository.findById(USER_1_ID);
        MATCHER.assertEquals(USER_1_VOTE, userVote);
    }

    @Test
    public void testFindByIdUserNotFound() throws Exception {
        Assert.assertNull(userVoteRepository.findById(UserTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testFindAll() throws Exception {
        List<UserVote> userVotes = userVoteRepository.findAll();
        MATCHER.assertCollectionsEquals(ALL_RESTAURANTS_VOTES, userVotes);
    }

    @Test
    public void testDelete() throws Exception {
        Assert.assertTrue(userVoteRepository.delete(USER_1_ID));
        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTE), userVoteRepository.findAll());

        Restaurant restaurant = restaurantRepository.findById(RESTAURANT_2_ID);
        Assert.assertEquals(RESTAURANT_1_VOTES_COUNT - 1, restaurant.getVotes());
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        Assert.assertFalse(userVoteRepository.delete(UserTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testShouldDeleteOnceOnly() throws Exception {
        Assert.assertTrue(userVoteRepository.delete(USER_1_ID));
        Assert.assertFalse(userVoteRepository.delete(USER_1_ID));

        Restaurant restaurant = restaurantRepository.findById(RESTAURANT_2_ID);
        Assert.assertEquals(RESTAURANT_1_VOTES_COUNT - 1, restaurant.getVotes());
    }

    @Test
    public void testReset() throws Exception {
        userVoteRepository.reset();
        MATCHER.assertCollectionsEquals(Collections.emptyList(), userVoteRepository.findAll());

        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurants) {
            Assert.assertEquals(0, restaurant.getVotes());
        }
    }
}
