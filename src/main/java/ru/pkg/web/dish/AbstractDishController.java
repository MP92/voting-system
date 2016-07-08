package ru.pkg.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Dish;
import ru.pkg.service.DishService;
import java.util.List;

import static ru.pkg.utils.EntityUtils.prepareToSave;

public abstract class AbstractDishController {

    @Autowired
    private DishService service;

    protected Dish create(Dish dish, int restaurantId) {
        dish.setId(null);
        return service.add(prepareToSave(dish), restaurantId);
    }

    protected Dish findById(int id, int restaurantId) {
        return service.findById(id, restaurantId);
    }

    protected List<Dish> findAll(int restaurantId) {
        return service.findAll(restaurantId);
    }

    protected void delete(int id, int restaurantId) {
        service.delete(id, restaurantId);
    }

    protected void update(int id, Dish dish, int restaurantId) {
        dish.setId(id);
        update(dish, restaurantId);
    }

    protected void update(Dish dish, int restaurantId) {
        service.update(prepareToSave(dish), restaurantId);
    }

    protected void changeInMenuState(int id, int restaurantId) {
        service.changeInMenuState(id, restaurantId);
    }
}
