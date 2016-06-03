package ru.pkg.repository;

import ru.pkg.model.Restaurant;

import java.util.Collection;

public interface RestaurantRepository {

    Restaurant findById(int id);

    boolean delete(int id);

    Restaurant save(Restaurant restaurant);

    Collection<Restaurant> findAll();

    Collection<Restaurant> findAllWithMenus();
}
