package ru.pkg.repository.hashmap;

import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;

import java.util.Collection;

public class HashMapRestaurantRepository implements RestaurantRepository {
    @Override
    public Restaurant findById(int id) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return null;
    }

    @Override
    public Collection<Restaurant> findAll() {
        return null;
    }

    @Override
    public Collection<Restaurant> findAllWithMenus() {
        return null;
    }
}
