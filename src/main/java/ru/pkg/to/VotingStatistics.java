package ru.pkg.to;

import ru.pkg.model.Restaurant;

public class VotingStatistics {

    private int restaurantId;

    private String restaurantName;

    private int votes;

    private double percentage;

    public VotingStatistics() {
    }

    public VotingStatistics(Restaurant r, Double sumVotes) {
        this.restaurantId = r.getId();
        this.restaurantName = r.getName();
        this.votes = r.getVotes();
        Double percentage = r.getVotes() / sumVotes;
        this.percentage = percentage.isNaN() ? 0 : percentage;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
