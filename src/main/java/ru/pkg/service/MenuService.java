package ru.pkg.service;

import ru.pkg.model.Dish;
import ru.pkg.model.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {

    void add(int restaurantId, int dishId);

    void add(Menu menu);

    void replace(Menu menu);

    List<Menu> findAll();

    Menu findById(int restaurantId);

    void delete(int restaurantId, int dishId);

    void deleteAll(int restaurantId);
}
