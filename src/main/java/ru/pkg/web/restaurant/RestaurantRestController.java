package ru.pkg.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pkg.model.Restaurant;
import java.net.URI;
import java.util.List;

import static ru.pkg.utils.constants.ControllerConstants.*;

@RestController
@RequestMapping(PATH_REST_RESTAURANT_LIST)
public class RestaurantRestController extends AbstractRestaurantController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(PATH_REST_RESTAURANT_LIST + PATH_ID).buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable(PATH_VAR_ID) int id, @RequestBody Restaurant restaurant) {
        super.update(id, restaurant);
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant findById(@PathVariable(PATH_VAR_ID) int id) {
        return super.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> findAll() {
        return super.findAllWithMenu();
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.DELETE)
    public void delete(@PathVariable(PATH_VAR_ID) int id) {
        super.delete(id);
    }
}
