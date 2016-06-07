package ru.pkg.web.restaurant;

import java.util.Collections;
import java.util.List;

public class DishIDsHolder {

    private int restaurantId;

    private List<Integer> dishIDs = Collections.emptyList();

    public DishIDsHolder() {
    }

    public DishIDsHolder(int restaurantId, List<Integer> dishIDs) {
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
        this.dishIDs = dishIDs != null ? dishIDs : Collections.emptyList();
    }
}
