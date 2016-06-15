package ru.pkg.repository;

import ru.pkg.model.Menu;

import java.util.List;

public interface MenuRepository {

    void save(int dishId, int restaurantId);

    void save(Menu menu);

    List<Menu> findAll();

    Menu findById(int restaurantId);

    boolean delete(int dishId, int restaurantId);

    void deleteAll(int restaurantId);
}
