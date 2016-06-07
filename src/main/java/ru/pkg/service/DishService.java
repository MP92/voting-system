package ru.pkg.service;

import ru.pkg.model.Dish;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

public interface DishService {

    Dish add(Dish dish) throws RestaurantNotFoundException;

    Dish findById(int id, int restaurantId) throws DishNotFoundException;

    List<Dish> findMenu(int restaurantId);

    List<Dish> findAll(int restaurantId);

    void update(Dish dish) throws DishNotFoundException;

    void delete(int id, int restaurantId) throws DishNotFoundException;
}
