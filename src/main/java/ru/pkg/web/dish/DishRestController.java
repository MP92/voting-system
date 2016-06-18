package ru.pkg.web.dish;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.pkg.model.Dish;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.utils.exception.RestaurantNotFoundException;
import ru.pkg.web.restaurant.RestaurantRestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(DishRestController.REST_URL)
public class DishRestController extends AbstractDishController {

    public static final String REST_URL = RestaurantRestController.REST_URL + "/{restaurantId}/dishes";

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Dish> createWithLocation(@RequestBody Dish dish, @PathVariable("restaurantId") int restaurantId) throws RestaurantNotFoundException {
        Dish created = super.create(dish, restaurantId);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL + "/{id}")
                    .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
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

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Dish> findAll(@PathVariable("restaurantId") int restaurantId) {
        return super.findAll(restaurantId);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) throws DishNotFoundException {
        super.delete(id, restaurantId);
    }
}
