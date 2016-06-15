package ru.pkg.to;

public class VotingStatistics {

    private int restaurantId;

    private String restaurantName;

    private int votes;

    private double percentage;

    public VotingStatistics() {
    }

    public VotingStatistics(int restaurantId, String restaurantName, int votes, double percentage) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.votes = votes;
        this.percentage = percentage;
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
