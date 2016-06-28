package ru.pkg.service;

import org.springframework.dao.DataAccessException;
import ru.pkg.model.Dish;
import ru.pkg.utils.exception.DishNotFoundException;

import java.util.List;

public interface DishService {

    Dish add(Dish dish, int restaurantId) throws DataAccessException;

    Dish findById(int id, int restaurantId) throws DishNotFoundException;

    List<Dish> findAll(int restaurantId);

    void update(Dish dish, int restaurantId) throws DishNotFoundException;

    void changeInMenuState(int id, int restaurantId);

    void delete(int id, int restaurantId) throws DishNotFoundException;
}
