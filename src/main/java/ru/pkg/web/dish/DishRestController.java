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
    public Dish create(@RequestBody Dish dish, @PathVariable("restaurantId") int restaurantId) throws RestaurantNotFoundException {
        return super.create(dish, restaurantId);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable("id") int id, @RequestBody Dish dish, @PathVariable("restaurantId") int restaurantId) throws DishNotFoundException {
        dish.setRestaurantId(restaurantId);
        super.update(id, dish, restaurantId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Dish findById(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) throws DishNotFoundException {
        return super.findById(id, restaurantId);
    }

    @RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Dish> findAll(@PathVariable("restaurantId") int restaurantId) {
        return super.findAll(restaurantId);
    }

    @RequestMapping(path = "/{dishId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) throws DishNotFoundException {
        super.delete(id, restaurantId);
    }
}
