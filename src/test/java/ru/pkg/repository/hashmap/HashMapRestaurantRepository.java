package ru.pkg.repository.hashmap;

import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;

import java.util.List;

public class HashMapRestaurantRepository implements RestaurantRepository {

    @Override
    public Restaurant save(Restaurant restaurant) {
        return null;
    }

    @Override
    public Restaurant findById(int id) {
        return null;
    }

    @Override
    public List<Restaurant> findAllWithMenu() {
        return null;
    }

    @Override
    public List<Restaurant> findAll() {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
