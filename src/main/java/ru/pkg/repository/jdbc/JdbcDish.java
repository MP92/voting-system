package ru.pkg.repository.jdbc;

import ru.pkg.model.Dish;

/**
 * Subclass of Dish that carries temporary restaurant id property which is only relevant for a JDBC implementation
 * of DishRepository.
 */
public class JdbcDish extends Dish {

    private int restaurantId;

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
