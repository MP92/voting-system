package ru.pkg.model;

import java.util.List;

public class Menu {

    private int restaurantId;

    private List<Integer> dishIDs;

    public Menu() {
    }

    public Menu(int restaurantId, List<Integer> dishIDs) {
        this.restaurantId = restaurantId;
        this.dishIDs = dishIDs;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<Integer> getDishIDs() {
        return dishIDs;
    }

    public void setDishIDs(List<Integer> dishIDs) {
        this.dishIDs = dishIDs;
    }

    public boolean isEmpty() {
        return dishIDs == null || dishIDs.isEmpty();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "restaurantId=" + restaurantId +
                ", dishIDs=" + dishIDs +
                '}';
    }
}
