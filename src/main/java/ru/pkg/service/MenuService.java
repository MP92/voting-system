package ru.pkg.service;

import ru.pkg.model.Menu;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

public interface MenuService {

    void add(int dishId, int restaurantId) throws DishNotFoundException;

    void add(Menu menu) throws DishNotFoundException;

    void replace(Menu menu) throws DishNotFoundException;

    List<Menu> findAll();

    Menu findById(int restaurantId) throws RestaurantNotFoundException;

    void delete(int dishId, int restaurantId) throws DishNotFoundException;

    void deleteAll(int restaurantId);
}
