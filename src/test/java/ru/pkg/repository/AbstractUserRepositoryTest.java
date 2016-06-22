package ru.pkg.repository;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.model.Restaurant;
import ru.pkg.model.User;
import ru.pkg.testdata.UserTestData;
import ru.pkg.testdata.RestaurantTestData;
import ru.pkg.utils.UserUtil;

import static ru.pkg.testdata.UserTestData.*;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_VOTES_COUNT;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractUserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void testAdd() {
        User toCreateUser = new TestUser(NEW_USER).asUser();
        User created = userRepository.save(toCreateUser);
        toCreateUser.setId(created.getId());
        MATCHER.assertEquals(toCreateUser, created);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, toCreateUser, USER_1, USER_2), userRepository.findAll());
    }

    @Test
    public void testFindById() {
        User user = userRepository.findById(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test
    public void testFindByIdNotFound() {
        Assert.assertNull(userRepository.findById(NOT_FOUND_INDEX));
    }

    @Test
    public void testFindAll() {
        MATCHER.assertCollectionsEquals(ALL_USERS, userRepository.findAll());
    }

    @Test
    public void testUpdate() {
        User toUpdateUser = new TestUser(USER_1_ID, NEW_USER).asUser();
        User updated = userRepository.save(toUpdateUser);
        Assert.assertTrue(updated.getId() == USER_1_ID);
        MATCHER.assertEquals(toUpdateUser, updated);
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, updated, USER_2), userRepository.findAll());
    }

    @Test
    public void testUpdateNotFound() {
        User toUpdateUser = new TestUser(NOT_FOUND_INDEX, NEW_USER).asUser();
        Assert.assertNull(userRepository.save(toUpdateUser));
        MATCHER.assertCollectionsEquals(ALL_USERS, userRepository.findAll());
    }

    @Test
    public void testDelete() {
        Assert.assertTrue(userRepository.delete(ADMIN_ID));

        MATCHER.assertCollectionsEquals(Arrays.asList(USER_1, USER_2), userRepository.findAll());
    }

    @Test
    public void testDeleteNotFound() {
        Assert.assertFalse(userRepository.delete(NOT_FOUND_INDEX));
        MATCHER.assertCollectionsEquals(ALL_USERS, userRepository.findAll());
    }

    @Test
    public void testSaveVote() throws Exception {
        userRepository.saveVote(USER_2_ID, RESTAURANT_1_ID);
        User user = userRepository.findById(USER_2_ID);
        RestaurantTestData.MATCHER.assertEquals(restaurantRepository.findById(RESTAURANT_1_ID), user.getChosenRestaurant());
        Assert.assertTrue(UserUtil.isUserVotedJustNow(user));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testShouldSaveVoteOnlyOnceAtDay() throws Exception {
        userRepository.saveVote(USER_1_ID, RESTAURANT_1_ID);
        try {
            userRepository.saveVote(USER_1_ID, RESTAURANT_1_ID);
        } catch (DataIntegrityViolationException e) {
            User user = userRepository.findById(USER_1_ID);
            Assert.assertEquals(RESTAURANT_1_VOTES_COUNT + 1, user.getChosenRestaurant().getVotes());
            throw e;
        }
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveVoteUserNotFound() throws Exception {
        userRepository.saveVote(NOT_FOUND_INDEX, RESTAURANT_1_ID);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveVoteRestaurantNotFound() throws Exception {
        userRepository.saveVote(USER_1_ID, RestaurantTestData.NOT_FOUND_INDEX);
    }

    @Test
    public void testDeleteVote() throws Exception {
        Restaurant chosenRestaurant = userRepository.findById(USER_1_ID).getChosenRestaurant();

        Assert.assertTrue(userRepository.deleteVote(USER_1_ID));
        User actual = userRepository.findById(USER_1_ID);
        Assert.assertNull(actual.getChosenRestaurant());
        Assert.assertNull(actual.getLastVoted());

        Assert.assertEquals(chosenRestaurant.getVotes() - 1, restaurantRepository.findById(chosenRestaurant.getId()).getVotes());
    }

    @Test
    public void testDeleteVoteUserNotFound() throws Exception {
        Assert.assertFalse(userRepository.deleteVote(UserTestData.NOT_FOUND_INDEX));
    }

    @Test
    public void testShouldDeleteVoteOnceOnly() throws Exception {
        Restaurant chosenRestaurant = userRepository.findById(USER_1_ID).getChosenRestaurant();

        Assert.assertTrue(userRepository.deleteVote(USER_1_ID));
        Assert.assertFalse(userRepository.deleteVote(USER_1_ID));

        Assert.assertEquals(chosenRestaurant.getVotes() - 1, restaurantRepository.findById(chosenRestaurant.getId()).getVotes());
    }

    @Test
    public void testResetVotes() throws Exception {
        userRepository.resetVotes();

        List<User> users = userRepository.findAll();
        for (User user : users) {
            Assert.assertNull(user.getLastVoted());
            Assert.assertNull(user.getChosenRestaurant());
        }

        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurants) {
            Assert.assertEquals(0, restaurant.getVotes());
        }
    }
}
