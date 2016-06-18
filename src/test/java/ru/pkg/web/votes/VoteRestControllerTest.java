package ru.pkg.web.votes;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import ru.pkg.LoggedUser;
import ru.pkg.model.Restaurant;
import ru.pkg.model.UserVote;
import ru.pkg.web.AbstractControllerTest;

import java.util.Collections;
import java.util.List;

import static ru.pkg.TestUtils.*;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_2_ID;
import static ru.pkg.testdata.RestaurantTestData.VOTING_STATISTICS;
import static ru.pkg.testdata.UserVoteTestData.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_BASE_URL = VoteRestController.REST_URL + "/votes/";
    private static final String RESTAURANT_1_VOTE_URL = String.format(VoteRestController.REST_URL + "/%d/vote", RESTAURANT_1_ID);

    @Test
    public void testVote() throws Exception {
        mockMvc.perform(post(RESTAURANT_1_VOTE_URL))
                .andExpect(status().isOk());

        UserVote actual = userVoteService.findById(LoggedUser.getId());
        UserVote expected = new UserVote(LoggedUser.getId(), RESTAURANT_1_ID, actual.getDateTime());
        MATCHER.assertEquals(expected, actual);

        Assert.assertEquals(RESTAURANT_1_VOTES_COUNT + 1, restaurantService.findById(RESTAURANT_1_ID).getVotes());
    }

    @Test
    public void testCancelVote() throws Exception {
        mockMvc.perform(post(REST_BASE_URL + "cancel"))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTE), userVoteService.findAll());
        Assert.assertEquals(RESTAURANT_2_VOTES_COUNT - 1, restaurantService.findById(RESTAURANT_2_ID).getVotes());
    }

    @Test
    public void testReset() throws Exception {
        mockMvc.perform(put(REST_BASE_URL + "reset"))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.emptyList(), userVoteService.findAll());

        List<Restaurant> restaurants = restaurantService.findAll();
        for (Restaurant restaurant : restaurants) {
            Assert.assertEquals(0, restaurant.getVotes());
        }
    }

    @Test
    public void testGetVotingStatistics() throws Exception {
        mockMvc.perform(get(REST_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonMatcher(VOTING_STATISTICS));
    }
}