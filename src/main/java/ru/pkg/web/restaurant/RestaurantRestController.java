package ru.pkg.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pkg.model.Restaurant;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = RestaurantRestController.REST_URL)
public class RestaurantRestController extends AbstractRestaurantController {

    public static final String REST_URL = "/rest/restaurants";

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("id") int id, @RequestBody Restaurant restaurant) throws RestaurantNotFoundException {
        super.update(id, restaurant);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant findById(@PathVariable("id") int id) throws RestaurantNotFoundException {
        return super.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> findAll() {
        return super.findAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws RestaurantNotFoundException {
        super.delete(id);
    }
}
