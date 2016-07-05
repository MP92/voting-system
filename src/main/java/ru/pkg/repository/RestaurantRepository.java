package ru.pkg.repository;

import ru.pkg.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Restaurant findById(int id);

    List<Restaurant> findAll();

    default List<Restaurant> findAllWithMenu() {
        return findAll();
    }

    boolean delete(int id);
}
