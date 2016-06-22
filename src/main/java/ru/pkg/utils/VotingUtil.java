package ru.pkg.utils;

import ru.pkg.model.Restaurant;
import ru.pkg.model.UserVote;
import ru.pkg.to.VotingStatistics;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VotingUtil {

    public static List<VotingStatistics> getVotingStatistics(List<Restaurant> restaurants, List<UserVote> votes) {
        Map<Integer, Long> votesMap = votes.stream().collect(Collectors.groupingBy(UserVote::getRestaurantId, Collectors.counting()));
        long sumVotes = votesMap.values().stream().mapToLong(Number::longValue).sum();

        return restaurants.stream().map(r -> new VotingStatistics(r, votesMap.getOrDefault(r.getId(), 0L), sumVotes)).collect(Collectors.toList());
    }
}
