package ru.pkg.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Dish;
import ru.pkg.service.DishService;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

public abstract class AbstractDishController {

    @Autowired
    DishService service;

    public Dish create(Dish dish, int restaurantId) throws RestaurantNotFoundException {
        dish.setId(null);
        return service.add(dish, restaurantId);
    }

    public Dish findById(int id, int restaurantId) throws DishNotFoundException {
        return service.findById(id, restaurantId);
    }

    public List<Dish> findAll(int restaurantId) {
        return service.findAll(restaurantId);
    }

    public void delete(int id, int restaurantId) throws DishNotFoundException {
        service.delete(id, restaurantId);
    }

    public void update(int id, Dish dish, int restaurantId) throws DishNotFoundException {
        dish.setId(id);
        service.update(dish, restaurantId);
    }

    public void update(Dish dish, int restaurantId) throws DishNotFoundException {
        service.update(dish, restaurantId);
    }

    public void changeInMenuState(int id, int restaurantId) {
        service.changeInMenuState(id, restaurantId);
    }
}
