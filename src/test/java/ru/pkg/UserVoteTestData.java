package ru.pkg;

import ru.pkg.matcher.ModelMatcher;
import ru.pkg.model.UserVote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static ru.pkg.UserTestData.ADMIN_ID;
import static ru.pkg.UserTestData.USER_1_ID;

import static ru.pkg.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.RestaurantTestData.RESTAURANT_2_ID;

public class UserVoteTestData {

    public static final ModelMatcher<UserVote, String> MATCHER = new ModelMatcher<>(UserVote::toString);

    private static final LocalDateTime DEFAULT_DATETIME = LocalDateTime.of(LocalDate.parse("2016-01-01"), LocalTime.MIDNIGHT);

    public static final UserVote ADMIN_VOTE = new UserVote(ADMIN_ID, RESTAURANT_1_ID, DEFAULT_DATETIME);
    public static final UserVote USER_1_VOTE = new UserVote(USER_1_ID, RESTAURANT_2_ID, DEFAULT_DATETIME);

    public static final List<UserVote> ALL_RESTAURANTS_VOTES = Arrays.asList(ADMIN_VOTE, USER_1_VOTE);

    public static final UserVote VOTE_RESTAURANT_NOT_FOUND = new UserVote(USER_1_ID, RestaurantTestData.NOT_FOUND_INDEX);
    public static final UserVote VOTE_USER_NOT_FOUND = new UserVote(UserTestData.NOT_FOUND_INDEX, RESTAURANT_1_ID);

    public static final int NOT_VOTED_USER_ID  = USER_1_ID + 1;

    public static final int RESTAURANT_1_VOTES_COUNT = 1;
    public static final int RESTAURANT_2_VOTES_COUNT = 1;
}
