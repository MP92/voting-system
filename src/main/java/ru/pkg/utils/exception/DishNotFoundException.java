package ru.pkg.utils.exception;

import ru.pkg.model.Dish;

public class DishNotFoundException extends RuntimeException {

    private static final String MSG_PATTERN = "Dish with id=%d not found for restaurant with id=%d";

    public DishNotFoundException(Dish dish, int restaurantId) {
        this(dish.getId(), restaurantId);
    }

    public DishNotFoundException(int id, int restaurantId) {
        super(String.format(MSG_PATTERN, id, restaurantId));
    }

    public DishNotFoundException(Throwable cause) {
        super(cause);
    }
}
