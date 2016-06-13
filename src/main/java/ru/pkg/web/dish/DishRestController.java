package ru.pkg.web.dish;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.pkg.model.Dish;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/rest/restaurants/{restaurantId}/dishes")
public class DishRestController extends AbstractDishController {

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Dish create(@PathVariable("restaurantId") int restaurantId, @RequestBody Dish dish) throws RestaurantNotFoundException {
        return super.create(restaurantId, dish);
    }

    @RequestMapping(path = "/{dishId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId, @RequestBody Dish dish) throws DishNotFoundException {
        dish.setRestaurantId(restaurantId);
        super.update(restaurantId, dishId, dish);
    }

    @RequestMapping(value = "/{dishId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Dish findById(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) throws DishNotFoundException {
        return super.findById(restaurantId, dishId);
    }

    @RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Dish> findAll(@PathVariable("restaurantId") int restaurantId) {
        return super.findAll(restaurantId);
    }

    @RequestMapping(path = "/{dishId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) throws DishNotFoundException {
        super.delete(restaurantId, dishId);
    }
}
