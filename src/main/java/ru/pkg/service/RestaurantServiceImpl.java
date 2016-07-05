package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

@Service
@CacheConfig(cacheNames = "restaurants")
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    @CacheEvict(allEntries = true)
    @Transactional
    public Restaurant add(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @Cacheable
    public Restaurant findById(int id) throws RestaurantNotFoundException {
        Restaurant restaurant = repository.findById(id);
        if (restaurant == null) {
            throw new RestaurantNotFoundException(id);
        }

        return restaurant;
    }

    @Cacheable(key = "#root.methodName")
    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    @Cacheable(key = "#root.methodName")
    public List<Restaurant> findAllWithMenu() {
        return repository.findAllWithMenu();
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public void update(Restaurant restaurant) throws RestaurantNotFoundException {
        if (repository.save(restaurant) == null) {
            throw new RestaurantNotFoundException(restaurant);
        }
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public void delete(int id) throws RestaurantNotFoundException {
        if (!repository.delete(id)) {
            throw new RestaurantNotFoundException(id);
        }
    }
}
