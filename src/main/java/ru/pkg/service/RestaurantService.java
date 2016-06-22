package ru.pkg.service;

import ru.pkg.model.Restaurant;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

public interface RestaurantService {

    Restaurant add(Restaurant restaurant);

    Restaurant findById(int id) throws RestaurantNotFoundException;

    List<Restaurant> findAll();

    void update(Restaurant restaurant) throws RestaurantNotFoundException;

    void delete(int id) throws RestaurantNotFoundException;
}