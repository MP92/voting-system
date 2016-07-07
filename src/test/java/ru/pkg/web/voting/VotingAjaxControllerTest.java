package ru.pkg.web.voting;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.pkg.TestUtils;
import ru.pkg.model.UserVote;
import ru.pkg.web.AbstractControllerTest;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import static ru.pkg.utils.constants.ControllerConstants.*;

@WithUserDetails(value = "User2", userDetailsServiceBeanName = "userService")
public class VotingAjaxControllerTest extends AbstractControllerTest {
    private static final String AJAX_BASE_URL = PATH_AJAX_RESTAURANT_LIST;
    private static final String RESTAURANT_1_VOTE_URL = String.format(PATH_AJAX_RESTAURANT_LIST + "/%d/vote", RESTAURANT_1_ID);

    @BeforeClass
    public static void setFakeVoteTimeBound() {
        TestUtils.setFakeVoteTimeBound();
    }

    @Test
    public void testVote() throws Exception {
        mockMvc.perform(post(RESTAURANT_1_VOTE_URL).with(csrf()))
                .andExpect(status().isOk());

        UserVote actual = votingService.findById(USER_1_ID);
        UserVote expected = new UserVote(USER_1_ID, RESTAURANT_2_ID, actual.getLastVoted());
        MATCHER.assertEquals(expected, actual);
    }

    @Test
    @WithAnonymousUser
    public void testVoteUnauth() throws Exception {
        mockMvc.perform(post(RESTAURANT_1_VOTE_URL).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(LOGIN_URL));
    }

    @Test
    @WithUserDetails(value = "User", userDetailsServiceBeanName = "userService")
    public void testCancel() throws Exception {
        mockMvc.perform(post(AJAX_BASE_URL + PATH_VOTE_CANCEL).with(csrf()))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTE), votingService.findAll());
    }

    @Test
    @WithAnonymousUser
    public void testCancelUnauth() throws Exception {
        mockMvc.perform(post(AJAX_BASE_URL + PATH_VOTE_CANCEL).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(LOGIN_URL));
    }

    @Test
    @WithUserDetails(value = "Admin", userDetailsServiceBeanName = "userService")
    public void testReset() throws Exception {
        mockMvc.perform(post(AJAX_BASE_URL + PATH_VOTE_RESET).with(csrf()))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.emptyList(), votingService.findAll());
    }

    @Test
    public void testResetForbidden() throws Exception {
        mockMvc.perform(put(AJAX_BASE_URL + PATH_VOTE_RESET).with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetVotingStatistics() throws Exception {
        mockMvc.perform(get(AJAX_BASE_URL + PATH_VOTING_STATISTICS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(VOTING_STATISTICS));
    }

    @Test
    @WithAnonymousUser
    public void testGetVotingStatisticsUnauth() throws Exception {
        mockMvc.perform(get(AJAX_BASE_URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(LOGIN_URL));
    }
}