package ru.pkg.repository;

import ru.pkg.model.Dish;
import java.util.List;

public interface DishRepository {

    Dish save(Dish dish, int restaurantId);

    Dish findById(int id, int restaurantId);

    List<Dish> findAll(int restaurantId);

    boolean delete(int id, int restaurantId);
}
