package ru.pkg.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Restaurant;
import ru.pkg.service.RestaurantService;
import java.util.List;

import static ru.pkg.utils.EntityUtils.prepareToSave;

public abstract class AbstractRestaurantController {

    @Autowired
    private RestaurantService service;

    protected Restaurant create(Restaurant restaurant) {
        restaurant.setId(null);
        return service.add(prepareToSave(restaurant));
    }

    protected Restaurant findById(int id) {
        return service.findById(id);
    }

    protected List<Restaurant> findAll() {
        return service.findAll();
    }

    protected List<Restaurant> findAllWithMenu() {
        return service.findAllWithMenu();
    }

    protected void delete(int id) {
        service.delete(id);
    }

    protected void update(int id, Restaurant restaurant) {
        restaurant.setId(id);
        service.update(prepareToSave(restaurant));
    }
}
