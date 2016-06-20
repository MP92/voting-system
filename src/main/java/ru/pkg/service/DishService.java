package ru.pkg.service;

import ru.pkg.model.Dish;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.Map;

public interface DishService {

    Dish add(Dish dish) throws RestaurantNotFoundException;

    Dish findById(int id, int restaurantId) throws DishNotFoundException;

    List<Dish> findAll(int restaurantId);

    Map<Integer, List<Dish>> findInAllMenus();

    List<Dish> findInMenu(int restaurantId);

    void update(Dish dish) throws DishNotFoundException;

    void changeInMenuState(int id, int restaurantId);

    void delete(int id, int restaurantId) throws DishNotFoundException;
}
