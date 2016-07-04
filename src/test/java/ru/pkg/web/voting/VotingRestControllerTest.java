package ru.pkg.web.voting;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import ru.pkg.model.UserVote;
import ru.pkg.utils.TimeUtil;
import ru.pkg.web.AbstractControllerTest;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalTime;
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

public class VotingRestControllerTest extends AbstractControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(VotingRestControllerTest.class);

    private static final String REST_BASE_URL = VotingRestController.REST_URL + "/voting";
    private static final String RESTAURANT_1_VOTE_URL = String.format(VotingRestController.REST_URL + "/%d/vote", RESTAURANT_1_ID);

    @BeforeClass
    public static void setFakeVoteTimeBound() {
        try {
            Field hourLimit = TimeUtil.class.getDeclaredField("HOUR_LIMIT");
            hourLimit.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(hourLimit, hourLimit.getModifiers() & ~Modifier.FINAL);
            hourLimit.set(null, LocalTime.now().plusHours(1));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOG.warn("Can't modify TimeUtil.HOUR_LIMIT value");
        }
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
        mockMvc.perform(post(REST_BASE_URL + "/cancel").with(userHttpBasic(USER_1)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.singletonList(ADMIN_VOTE), votingService.findAll());
    }

    @Test
    public void testCancelUnauth() throws Exception {
        mockMvc.perform(post(REST_BASE_URL + "/cancel"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testReset() throws Exception {
        mockMvc.perform(put(REST_BASE_URL + "/reset").with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        MATCHER.assertCollectionsEquals(Collections.emptyList(), votingService.findAll());
    }

    @Test
    public void testResetForbidden() throws Exception {
        mockMvc.perform(put(REST_BASE_URL + "/reset").with(userHttpBasic(USER_1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetVotingStatistics() throws Exception {
        mockMvc.perform(get(REST_BASE_URL).with(userHttpBasic(USER_1)))
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