package ru.pkg.service;

import ru.pkg.model.Restaurant;
import java.util.List;

public interface RestaurantService {

    Restaurant add(Restaurant restaurant);

    Restaurant findById(int id);

    List<Restaurant> findAll();

    List<Restaurant> findAllWithMenu();

    void update(Restaurant restaurant);

    void delete(int id);
}