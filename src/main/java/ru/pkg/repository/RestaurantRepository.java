package ru.pkg.repository;

import ru.pkg.model.Restaurant;

import java.util.List;
import java.util.Map;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Restaurant findById(int id);

    List<Restaurant> findAll();

    boolean delete(int id);
}
