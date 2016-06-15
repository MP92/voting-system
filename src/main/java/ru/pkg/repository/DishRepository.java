package ru.pkg.repository;

import org.springframework.dao.DataIntegrityViolationException;
import ru.pkg.model.Dish;
import ru.pkg.utils.exception.DishNotFoundException;

import java.util.List;
import java.util.Map;

public interface DishRepository {

    Dish save(Dish dish) throws DataIntegrityViolationException;

    Dish findById(int id, int restaurantId);

    List<Dish> findAll(int restaurantId);

    boolean delete(int id, int restaurantId);

    Map<Integer, List<Dish>> findInAllMenus();

    List<Dish> findInMenu(int restaurantId);
}
