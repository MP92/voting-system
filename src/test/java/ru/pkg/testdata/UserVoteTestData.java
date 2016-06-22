package ru.pkg.testdata;

import ru.pkg.matcher.ModelMatcher;
import ru.pkg.model.UserVote;
import ru.pkg.to.VotingStatistics;
import ru.pkg.utils.VotingUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static ru.pkg.testdata.UserTestData.ADMIN_ID;
import static ru.pkg.testdata.UserTestData.USER_1_ID;

import static ru.pkg.testdata.RestaurantTestData.ALL_RESTAURANTS;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.testdata.RestaurantTestData.RESTAURANT_2_ID;

public class UserVoteTestData {

    public static final ModelMatcher<UserVote, String> MATCHER = new ModelMatcher<>(UserVote::toString);

    public static final LocalDateTime VOTE_DT = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);

    public static final UserVote ADMIN_VOTE = new UserVote(ADMIN_ID, RESTAURANT_1_ID, VOTE_DT);
    public static final UserVote USER_1_VOTE = new UserVote(USER_1_ID, RESTAURANT_2_ID, VOTE_DT);

    public static final UserVote EMPTY_VOTE = new UserVote(UserTestData.NOT_FOUND_INDEX);

    public static final List<UserVote> ALL_USER_VOTES = Arrays.asList(ADMIN_VOTE, USER_1_VOTE);

    public static final UserVote VOTE_RESTAURANT_NOT_FOUND = new UserVote(USER_1_ID, RestaurantTestData.NOT_FOUND_INDEX);
    public static final UserVote VOTE_USER_NOT_FOUND = new UserVote(UserTestData.NOT_FOUND_INDEX, RESTAURANT_1_ID);

    public static final List<VotingStatistics> VOTING_STATISTICS = VotingUtil.getVotingStatistics(ALL_RESTAURANTS, ALL_USER_VOTES);
}
