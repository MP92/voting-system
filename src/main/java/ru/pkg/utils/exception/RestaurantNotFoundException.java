package ru.pkg.utils.exception;

import ru.pkg.model.Restaurant;

public class RestaurantNotFoundException extends NotFoundException {

    private static final String MSG_PATTERN = "Restaurant with id=%d not found";

    public RestaurantNotFoundException(Restaurant restaurant) {
        this(restaurant.getId());
    }

    public RestaurantNotFoundException(int id) {
        super(String.format(MSG_PATTERN, id));
    }

    public RestaurantNotFoundException(Throwable cause) {
        super(cause);
    }
}
