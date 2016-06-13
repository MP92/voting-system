package ru.pkg.web.menu;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.Dish;
import ru.pkg.model.Menu;
import ru.pkg.model.Restaurant;
import ru.pkg.service.DishService;
import ru.pkg.service.MenuService;
import ru.pkg.service.RestaurantService;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.Map;

public abstract class AbstractMenuController {

    @Autowired
    MenuService menuService;

    @Autowired
    RestaurantService restaurantService;

    public Menu findById(int restaurantId) {
        return menuService.findById(restaurantId);
    }

    public List<Menu> findAll() {
        return menuService.findAll();
    }

    public void update(int restaurantId, Menu menu) {
        menu.setRestaurantId(restaurantId);
        menuService.replace(menu);
    }

    public void delete(int restaurantId, int dishId) {
        menuService.delete(restaurantId, dishId);
    }
}
