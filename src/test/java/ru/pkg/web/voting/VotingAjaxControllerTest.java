package ru.pkg.web.voting;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.pkg.model.UserVote;
import ru.pkg.web.AbstractControllerTest;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.pkg.TestUtils.jsonMatcher;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_2_ID;
import static ru.pkg.testdata.UserVoteTestData.ADMIN_VOTE;
import static ru.pkg.testdata.UserVoteTestData.MATCHER;
import static ru.pkg.testdata.UserVoteTestData.VOTING_STATISTICS;
import static ru.pkg.testdata.UserTestData.USER_1_ID;

@WithUserDetails(value = "User", userDetailsServiceBeanName = "userService")
public class VotingAjaxControllerTest extends AbstractControllerTest {

    private static final String AJAX_BASE_URL = VotingAjaxController.AJAX_URL + "/voting";
    private static final String RESTAURANT_1_VOTE_URL = String.format(VotingAjaxController.AJAX_URL + "/%d/vote", RESTAURANT_1_ID);

    @Test
    public void testVote() throws Exception {
        mockMvc.perform(post(RESTAURANT_1_VOTE_URL))
                .andExpect(status().isOk());

        UserVote actual = votingService.findById(USER_1_ID);
        UserVote expected = new UserVote(USER_1_ID, RESTAURANT_2_ID, actual.getLastVoted());
        MATCHER.assertEquals(expected, actual);
    }

    @Test
    @WithAnonymousUser
    public void testVoteUnauth() throws Exception {
        mockMvc.perform(post(RESTAURANT_1_VOTE_URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testCancel() throws Exception {
        mockMvc.perform(post(AJAX_BASE_URL + "/cancel"))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTE), votingService.findAll());
    }

    @Test
    @WithAnonymousUser
    public void testCancelUnauth() throws Exception {
        mockMvc.perform(post(AJAX_BASE_URL + "/cancel"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithUserDetails(value = "Admin", userDetailsServiceBeanName = "userService")
    public void testReset() throws Exception {
        mockMvc.perform(post(AJAX_BASE_URL + "/reset"))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.emptyList(), votingService.findAll());
    }

    @Test
    public void testResetForbidden() throws Exception {
        mockMvc.perform(put(AJAX_BASE_URL + "/reset"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetVotingStatistics() throws Exception {
        mockMvc.perform(get(AJAX_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(VOTING_STATISTICS));
    }

    @Test
    @WithAnonymousUser
    public void testGetVotingStatisticsUnauth() throws Exception {
        mockMvc.perform(get(AJAX_BASE_URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}