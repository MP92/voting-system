package ru.pkg.to;

import ru.pkg.model.Restaurant;

public class VotingStatistics {

    private final int restaurantId;

    private final String restaurantName;

    private final long votes;

    private final double percentage;

    public VotingStatistics(Restaurant restaurant, long votes, long sumVotes) {
        this.restaurantId = restaurant.getId();
        this.restaurantName = restaurant.getName();
        this.votes = votes;
        this.percentage = sumVotes > 0 ? votes * 1.0 / sumVotes : 0;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public long getVotes() {
        return votes;
    }

    public double getPercentage() {
        return percentage;
    }
}
