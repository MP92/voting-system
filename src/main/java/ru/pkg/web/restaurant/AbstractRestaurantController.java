package ru.pkg.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Restaurant;
import ru.pkg.service.RestaurantService;
import ru.pkg.service.VotesService;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

public abstract class AbstractRestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    VotesService votesService;

    public Restaurant create(Restaurant restaurant) {
        restaurant.setId(null);
        return restaurantService.add(restaurant);
    }

    public Restaurant findById(int id) throws RestaurantNotFoundException {
        return restaurantService.findById(id);
    }

    public List<Restaurant> findAll() {
        return restaurantService.findAll();
    }

    public List<Restaurant> findAllWithMenu() {
        return restaurantService.findAllWithMenu();
    }

    public void delete(int id) throws RestaurantNotFoundException {
        restaurantService.delete(id);
    }

    public void update(int id, Restaurant restaurant) throws RestaurantNotFoundException {
        restaurant.setId(id);
        restaurantService.update(restaurant);
    }
}
