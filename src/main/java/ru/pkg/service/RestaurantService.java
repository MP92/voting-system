package ru.pkg.service;

import ru.pkg.model.Restaurant;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.Collection;

public interface RestaurantService {

    Restaurant findById(int id) throws RestaurantNotFoundException;

    void delete(int id) throws RestaurantNotFoundException;

    Restaurant add(Restaurant restaurant);

    void update(Restaurant restaurant) throws RestaurantNotFoundException;

    Collection<Restaurant> findAll();
}
