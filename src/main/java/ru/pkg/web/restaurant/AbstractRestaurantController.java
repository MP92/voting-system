package ru.pkg.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Restaurant;
import ru.pkg.service.RestaurantService;
import ru.pkg.to.RestaurantWithVotes;
import ru.pkg.utils.RestaurantUtil;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.Objects;

public abstract class AbstractRestaurantController {

    @Autowired
    RestaurantService service;

    public Restaurant create(Restaurant restaurant) {
        restaurant.setId(null);
        return service.add(restaurant);
    }

    public Restaurant findById(int id) throws RestaurantNotFoundException {
        return service.findById(id);
    }

    public List<RestaurantWithVotes> findAll() {
        return RestaurantUtil.getWithVotes(service.findAll(), service.findAllVotes());
    }

    public List<RestaurantWithVotes> findAllWithMenu() {
        return RestaurantUtil.getWithVotes(service.findAllWithMenu(), service.findAllVotes());
    }

    public void delete(int id) throws RestaurantNotFoundException {
        service.delete(id);
    }

    public void update(Restaurant restaurant) throws RestaurantNotFoundException {
        Objects.requireNonNull(restaurant.getId());
        service.update(restaurant);
    }

    public void resetVotes() {
        service.resetVotes();
    }

    public void addDishToMenu(int id, int dishId) {
        service.addDishToMenu(id, dishId);
    }

    public void deleteDishFromMenu(int id, int dishId) {
        service.deleteDishFromMenu(id, dishId);
    }
}
