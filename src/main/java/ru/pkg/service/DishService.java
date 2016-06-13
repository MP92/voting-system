package ru.pkg.service;

import ru.pkg.model.Dish;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.Map;

public interface DishService {

    Dish add(Dish dish) throws RestaurantNotFoundException;

    Dish findById(int restaurantId, int dishId) throws DishNotFoundException;

    List<Dish> findAll(int restaurantId);

    Map<Integer, List<Dish>> findInAllMenus();

    List<Dish> findInMenu(int restaurantId);

    void update(Dish dish) throws DishNotFoundException;

    void delete(int restaurantId, int dishId) throws DishNotFoundException;
}
