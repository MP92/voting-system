package ru.pkg.service;

import ru.pkg.model.Dish;
import java.util.List;

public interface DishService {

    Dish add(Dish dish, int restaurantId);

    Dish findById(int id, int restaurantId);

    List<Dish> findAll(int restaurantId);

    void update(Dish dish, int restaurantId);

    void changeInMenuState(int id, int restaurantId);

    void delete(int id, int restaurantId);
}
