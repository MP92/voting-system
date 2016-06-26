package ru.pkg.web.voting;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.pkg.LoggedUser;
import ru.pkg.model.UserVote;
import ru.pkg.web.AbstractControllerTest;

import java.util.Collections;

import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_2_ID;
import static ru.pkg.testdata.UserVoteTestData.*;
import static ru.pkg.TestUtils.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class VotingRestControllerTest extends AbstractControllerTest {

    private static final String REST_BASE_URL = VotingRestController.REST_URL + "/voting/";
    private static final String RESTAURANT_1_VOTE_URL = String.format(VotingRestController.REST_URL + "/%d/vote", RESTAURANT_1_ID);

    @Test
    public void testVote() throws Exception {
        mockMvc.perform(post(RESTAURANT_1_VOTE_URL))
                .andExpect(status().isOk());

        UserVote actual = votingService.findById(LoggedUser.getId());
        UserVote expected = new UserVote(LoggedUser.getId(), RESTAURANT_2_ID, actual.getLastVoted());
        MATCHER.assertEquals(expected, actual);
    }

    @Test
    public void testCancel() throws Exception {
        mockMvc.perform(post(REST_BASE_URL + "cancel"))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTE), votingService.findAll());
    }

    @Test
    public void testReset() throws Exception {
        mockMvc.perform(put(REST_BASE_URL + "reset"))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.emptyList(), votingService.findAll());
    }

    @Test
    public void testGetVotingStatistics() throws Exception {
        mockMvc.perform(get(REST_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(VOTING_STATISTICS));
    }
}