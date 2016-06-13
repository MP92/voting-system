package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Restaurant;
import ru.pkg.repository.DishRepository;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.Map;

import static ru.pkg.utils.RestaurantUtil.getWithMenus;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;


    @Override
    public Restaurant add(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional(readOnly = true)
    @Override
    public Restaurant findById(int id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id);
        if (restaurant == null) {
            throw new RestaurantNotFoundException(id);
        }
        restaurant.setMenu(dishRepository.findInMenu(id));

        return restaurant;
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Restaurant> findAllWithMenu() {
        return getWithMenus(restaurantRepository.findAll(), dishRepository.findInAllMenus());
    }

    @Override
    public void update(Restaurant restaurant) throws RestaurantNotFoundException {
        if (restaurantRepository.save(restaurant) == null) {
            throw new RestaurantNotFoundException(restaurant);
        }
    }

    @Override
    public void delete(int id) throws RestaurantNotFoundException {
        if (!restaurantRepository.delete(id)) {
            throw new RestaurantNotFoundException(id);
        }
    }
}
