package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    @Override
    public Restaurant add(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @Override
    public Restaurant findById(int id) throws RestaurantNotFoundException {
        Restaurant restaurant = repository.findById(id);
        if (restaurant == null) {
            throw new RestaurantNotFoundException(id);
        }
        return restaurant;
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Restaurant> findAllWithMenu() {
        return repository.findAllWithMenu();
    }

    @Override
    public void update(Restaurant restaurant) throws RestaurantNotFoundException {
        if (repository.save(restaurant) == null) {
            throw new RestaurantNotFoundException(restaurant);
        }
    }

    @Override
    public void delete(int id) throws RestaurantNotFoundException {
        if (!repository.delete(id)) {
            throw new RestaurantNotFoundException(id);
        }
    }
}
