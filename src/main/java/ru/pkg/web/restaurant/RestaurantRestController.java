package ru.pkg.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.pkg.model.Restaurant;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/restaurants")
public class RestaurantRestController extends AbstractRestaurantController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return super.create(restaurant);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable("id") int id, @RequestBody Restaurant restaurant) throws RestaurantNotFoundException {
        super.update(id, restaurant);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Restaurant findById(@PathVariable("id") int id) throws RestaurantNotFoundException {
        return super.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Restaurant> findAll() {
        return super.findAll();
    }

    @RequestMapping(path = "/withmenu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Restaurant> findAllWithMenu() {
        return super.findAllWithMenu();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws RestaurantNotFoundException {
        super.delete(id);
    }
}
