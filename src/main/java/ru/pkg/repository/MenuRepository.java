package ru.pkg.repository;

import ru.pkg.model.Menu;

import java.util.List;

public interface MenuRepository {

    void save(int restaurantId, int dishId);

    void save(Menu menu);

    List<Menu> findAll();

    Menu findById(int restaurantId);

    boolean delete(int restaurantId, int dishId);

    void deleteAll(int restaurantId);
}
