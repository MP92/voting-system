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
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @CacheEvict(allEntries = true)
    public Restaurant add(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Cacheable
    @Transactional(readOnly = true)
    public Restaurant findById(int id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id);
        if (restaurant == null) {
            throw new RestaurantNotFoundException(id);
        }

        return restaurant;
    }

    @Cacheable
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @CacheEvict(allEntries = true)
    public void update(Restaurant restaurant) throws RestaurantNotFoundException {
        if (restaurantRepository.save(restaurant) == null) {
            throw new RestaurantNotFoundException(restaurant);
        }
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) throws RestaurantNotFoundException {
        if (!restaurantRepository.delete(id)) {
            throw new RestaurantNotFoundException(id);
        }
    }
}
