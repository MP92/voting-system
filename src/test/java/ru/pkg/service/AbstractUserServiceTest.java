package ru.pkg.service;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.model.Restaurant;
import ru.pkg.model.User;
import ru.pkg.testdata.RestaurantTestData;
import ru.pkg.testdata.UserTestData;
import ru.pkg.utils.UserUtil;
import ru.pkg.utils.exception.UserNotFoundException;
import ru.pkg.utils.exception.VotingException;

import java.util.Arrays;
import java.util.List;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;

import static ru.pkg.testdata.UserTestData.*;
import static ru.pkg.testdata.UserTestData.NEW_USER;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RestaurantService restaurantService;

    @After
    public void tearDown() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void testAdd() {
        User toCreateUser = new TestUser(NEW_USER);
        User created = userService.add(toCreateUser);
        toCreateUser.setId(created.getId());
        MATCHER.assertCollectionsEquals(Arrays.asList(ADMIN, toCreateUser, USER_1, USER_2), userService.findAll());
    }
    
    @Test
    public void testFindById() {
        User user = userService.findById(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindByIdNotFound() {
        userService.findById(1000);
    }

    @Test
    public void testFindAll() {
        MATCHER.assertCollectionsEquals(ALL_USERS, userService.findAll());
    }
    
    @Test
    public void testUpdate() {
        TestUser toUpdateUser = new TestUser(ADMIN_ID, NEW_USER);
        userService.update(toUpdateUser.asUser());
        MATCHER.assertCollectionsEquals(Arrays.asList(toUpdateUser, USER_1, USER_2), userService.findAll());
    }
    
    @Test(expected = UserNotFoundException.class)
    public void testUpdateNotFound() {
        userService.update(new TestUser(10000, NEW_USER));
    }
    
    @Test
    public void testDelete() {
        userService.delete(ADMIN_ID);
        MATCHER.assertCollectionsEquals(Arrays.asList(USER_1, USER_2), userService.findAll());
    }
    
    @Test(expected = UserNotFoundException.class)
    public void testDeleteNotFound() {
        userService.delete(1000);
    }


    @Test
    public void testSaveVote() throws Exception {
        userService.saveVote(USER_2_ID, RESTAURANT_1_ID);
        User user = userService.findById(USER_2_ID);
        RestaurantTestData.MATCHER.assertEquals(restaurantService.findById(RESTAURANT_1_ID), user.getChosenRestaurant());
        Assert.assertTrue(UserUtil.isUserVotedJustNow(user));
    }

    @Test(expected = VotingException.class)
    public void testShouldSaveVoteOnlyOnceAtDay() throws Exception {
        userService.saveVote(USER_1_ID, RESTAURANT_1_ID);
        userService.saveVote(USER_1_ID, RESTAURANT_1_ID);
    }

    @Test(expected = VotingException.class)
    public void testSaveVoteUserNotFound() throws Exception {
        userService.saveVote(NOT_FOUND_INDEX, RESTAURANT_1_ID);
    }

    @Test(expected = VotingException.class)
    public void testSaveVoteRestaurantNotFound() throws Exception {
        userService.saveVote(USER_1_ID, RestaurantTestData.NOT_FOUND_INDEX);
    }

    @Test
    public void testDeleteVote() throws Exception {
        Restaurant chosenRestaurant = userService.findById(USER_1_ID).getChosenRestaurant();

        userService.deleteVote(USER_1_ID);
        User actual = userService.findById(USER_1_ID);
        Assert.assertNull(actual.getChosenRestaurant());
        Assert.assertNull(actual.getLastVoted());

        Assert.assertEquals(chosenRestaurant.getVotes() - 1, restaurantService.findById(chosenRestaurant.getId()).getVotes());
    }

    @Test(expected = VotingException.class)
    public void testDeleteVoteUserNotFound() throws Exception {
        userService.deleteVote(UserTestData.NOT_FOUND_INDEX);
    }

    @Test(expected = VotingException.class)
    public void testShouldDeleteVoteOnceOnly() throws Exception {
        userService.deleteVote(USER_1_ID);
        userService.deleteVote(USER_1_ID);
    }

    @Test
    public void testResetVotes() throws Exception {
        userService.resetVotes();

        List<User> users = userService.findAll();
        for (User user : users) {
            Assert.assertNull(user.getLastVoted());
            Assert.assertNull(user.getChosenRestaurant());
        }

        List<Restaurant> restaurants = restaurantService.findAll();
        for (Restaurant restaurant : restaurants) {
            Assert.assertEquals(0, restaurant.getVotes());
        }
    }
}
