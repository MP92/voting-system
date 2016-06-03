package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.Collection;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static final String EXCEPTION_MSG_PATTERN = "Restaurant with id=%d not found";

    @Autowired
    private RestaurantRepository repository;

    @Override
    public Restaurant findById(int id) throws RestaurantNotFoundException {
        Restaurant restaurant = repository.findById(id);
        if (restaurant == null) {
            throw new RestaurantNotFoundException(String.format(EXCEPTION_MSG_PATTERN, id));
        }
        return restaurant;
    }

    @Override
    public void delete(int id) throws RestaurantNotFoundException {
        if (!repository.delete(id)) {
            throw new RestaurantNotFoundException(String.format(EXCEPTION_MSG_PATTERN, id));
        }
    }

    @Override
    public Restaurant add(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @Override
    public void update(Restaurant restaurant) throws RestaurantNotFoundException {
        if (repository.save(restaurant) == null) {
            throw new RestaurantNotFoundException(String.format(EXCEPTION_MSG_PATTERN, restaurant.getId()));
        }
    }

    @Override
    public Collection<Restaurant> findAll() {
        return repository.findAll();
    }
}
