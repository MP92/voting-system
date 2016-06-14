package ru.pkg.model;

public class Votes {

    private int restaurantId;

    private String restaurantName;

    private int count;

    public Votes() {
    }

    public Votes(int restaurantId, String restaurantName, int count) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.count = count;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Votes{" +
                "restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", count=" + count +
                '}';
    }
}
