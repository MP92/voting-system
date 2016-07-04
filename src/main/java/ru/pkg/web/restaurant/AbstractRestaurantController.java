package ru.pkg.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Restaurant;
import ru.pkg.service.RestaurantService;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

import static ru.pkg.utils.EntityUtils.prepareToSave;

public abstract class AbstractRestaurantController {

    @Autowired
    private RestaurantService service;

    public Restaurant create(Restaurant restaurant) {
        restaurant.setId(null);
        return service.add(prepareToSave(restaurant));
    }

    public Restaurant findById(int id) throws RestaurantNotFoundException {
        return service.findById(id);
    }

    public List<Restaurant> findAll() {
        return service.findAll();
    }

    public void delete(int id) throws RestaurantNotFoundException {
        service.delete(id);
    }

    public void update(int id, Restaurant restaurant) throws RestaurantNotFoundException {
        restaurant.setId(id);
        service.update(prepareToSave(restaurant));
    }
}
