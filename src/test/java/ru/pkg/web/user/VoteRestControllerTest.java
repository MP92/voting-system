package ru.pkg.web.user;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ru.pkg.LoggedUser;
import ru.pkg.model.Restaurant;
import ru.pkg.model.User;
import ru.pkg.testdata.RestaurantTestData;
import ru.pkg.utils.UserUtil;
import ru.pkg.web.AbstractControllerTest;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_BASE_URL = VoteRestController.REST_URL + "/votes/";
    private static final String RESTAURANT_1_VOTE_URL = String.format(VoteRestController.REST_URL + "/%d/vote", RESTAURANT_1_ID);

    @After
    public void tearDown() throws Exception {
        cacheManager.getCache("restaurants").clear();
    }

    @Test
    public void testVote() throws Exception {
        mockMvc.perform(post(RESTAURANT_1_VOTE_URL))
                .andExpect(status().isOk());

        User user = userService.findById(LoggedUser.getId());
        RestaurantTestData.MATCHER.assertEquals(restaurantService.findById(RESTAURANT_1_ID), user.getChosenRestaurant());
        Assert.assertTrue(UserUtil.isUserVotedJustNow(user));
    }

    @Test
    public void testCancelVote() throws Exception {
        Restaurant chosenRestaurant = userService.findById(LoggedUser.getId()).getChosenRestaurant();

        mockMvc.perform(post(REST_BASE_URL + "cancel"))
                .andExpect(status().isOk());

        User actual = userService.findById(LoggedUser.getId());
        Assert.assertNull(actual.getChosenRestaurant());
        Assert.assertNull(actual.getLastVoted());

        Assert.assertEquals(chosenRestaurant.getVotes() - 1, restaurantService.findById(chosenRestaurant.getId()).getVotes());
    }
}