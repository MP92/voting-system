package ru.pkg.web.voting;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;
import ru.pkg.model.UserVote;
import ru.pkg.web.AbstractControllerTest;
import java.util.Collections;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_2_ID;
import static ru.pkg.testdata.UserTestData.ADMIN;
import static ru.pkg.testdata.UserTestData.USER_1;
import static ru.pkg.testdata.UserTestData.USER_2;
import static ru.pkg.testdata.UserTestData.USER_1_ID;
import static ru.pkg.testdata.UserVoteTestData.*;
import static ru.pkg.TestUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.pkg.utils.constants.ControllerConstants.*;

public class VotingRestControllerTest extends AbstractControllerTest {
    private static final String REST_BASE_URL = PATH_REST_RESTAURANT_LIST;
    private static final String RESTAURANT_1_VOTE_URL = String.format(PATH_REST_RESTAURANT_LIST + "/%d/vote", RESTAURANT_1_ID);

    @BeforeClass
    public static void beforeClass() {
        setFakeVoteTimeBound();
    }

    @Test
    public void testVote() throws Exception {
        mockMvc.perform(post(RESTAURANT_1_VOTE_URL).with(userHttpBasic(USER_2)))
                .andExpect(status().isOk());

        UserVote actual = votingService.findById(USER_1_ID);
        UserVote expected = new UserVote(USER_1_ID, RESTAURANT_2_ID, actual.getLastVoted());
        MATCHER.assertEquals(expected, actual);
    }

    @Test
    public void testVoteUnauth() throws Exception {
        mockMvc.perform(post(RESTAURANT_1_VOTE_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCancel() throws Exception {
        mockMvc.perform(post(REST_BASE_URL + PATH_VOTE_CANCEL).with(userHttpBasic(USER_1)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTE), votingService.findAll());
    }

    @Test
    public void testCancelUnauth() throws Exception {
        mockMvc.perform(post(REST_BASE_URL + PATH_VOTE_CANCEL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testReset() throws Exception {
        mockMvc.perform(put(REST_BASE_URL + PATH_VOTE_RESET).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.emptyList(), votingService.findAll());
    }

    @Test
    public void testResetForbidden() throws Exception {
        mockMvc.perform(put(REST_BASE_URL + PATH_VOTE_RESET).with(userHttpBasic(USER_1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetVotingStatistics() throws Exception {
        mockMvc.perform(get(REST_BASE_URL + PATH_VOTING_STATISTICS).with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(VOTING_STATISTICS));
    }

    @Test
    public void testGetVotingStatisticsUnauth() throws Exception {
        mockMvc.perform(get(REST_BASE_URL))
                .andExpect(status().isUnauthorized());
    }
}