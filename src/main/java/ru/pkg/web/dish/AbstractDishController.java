package ru.pkg.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Dish;
import ru.pkg.service.DishService;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.Objects;

public abstract class AbstractDishController {

    @Autowired
    DishService service;

    public Dish create(int restaurantId, Dish dish) throws RestaurantNotFoundException {
        dish.setRestaurantId(restaurantId);
        dish.setId(null);
        return service.add(dish);
    }

    public Dish findById(int restaurantId, int dishId) throws DishNotFoundException {
        return service.findById(restaurantId, dishId);
    }

    public List<Dish> findAll(int restaurantId) {
        return service.findAll(restaurantId);
    }

    public void delete(int restaurantId, int dishId) throws DishNotFoundException {
        service.delete(restaurantId, dishId);
    }

    public void update(int restaurantId, int dishId, Dish dish) throws DishNotFoundException {
        dish.setRestaurantId(restaurantId);
        dish.setId(dishId);
        service.update(dish);
    }
}
