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

    public Dish create(Dish dish) throws RestaurantNotFoundException {
        dish.setId(null);
        return service.add(dish);
    }

    public Dish findById(int id, int restaurantId) throws DishNotFoundException {
        return service.findById(id, restaurantId);
    }

    public List<Dish> findMenu(int restaurantId) {
        return service.findMenu(restaurantId);
    }

    public List<Dish> findAll(int restaurantId) {
        return service.findAll(restaurantId);
    }

    public void delete(int id, int restaurantId) throws DishNotFoundException {
        service.delete(id, restaurantId);
    }

    public void update(Dish dish) throws DishNotFoundException {
        Objects.requireNonNull(dish.getId());
        service.update(dish);
    }


}
