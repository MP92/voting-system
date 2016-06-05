package ru.pkg.repository;

import ru.pkg.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Restaurant findById(int id);

    List<Restaurant> findAll();

    List<Restaurant> findAllWithMenu();

    boolean delete(int id);
}
