package ru.pkg;

import ru.pkg.matcher.ModelMatcher;
import ru.pkg.model.Votes;

import java.util.Arrays;
import java.util.List;

import static ru.pkg.RestaurantTestData.RESTAURANT_1_ID;
import static ru.pkg.RestaurantTestData.RESTAURANT_2_ID;
import static ru.pkg.RestaurantTestData.RESTAURANT_1;
import static ru.pkg.RestaurantTestData.RESTAURANT_2;

public class VotesTestData {

    public static final ModelMatcher<Votes, String> MATCHER = new ModelMatcher<>(Votes::toString);

    public static final int R_1_VOTES_COUNT = 5;

    public static final Votes RESTAURANT_1_VOTES = new Votes(RESTAURANT_1_ID, RESTAURANT_1.getName(), 5);
    public static final Votes RESTAURANT_2_VOTES = new Votes(RESTAURANT_2_ID, RESTAURANT_2.getName(), 10);

    public static final List<Votes> ALL_VOTES = Arrays.asList(RESTAURANT_1_VOTES, RESTAURANT_2_VOTES);

    public static final List<Votes> AFTER_RESET_ALL_VOTES =
            Arrays.asList(VotesUtil.getWithoutVotes(RESTAURANT_1_VOTES), VotesUtil.getWithoutVotes(RESTAURANT_2_VOTES));

    static class VotesUtil {
        public static Votes getWithoutVotes(Votes votes) {
            return new Votes(votes.getRestaurantId(), votes.getRestaurantName(), 0);
        }
    }
}
