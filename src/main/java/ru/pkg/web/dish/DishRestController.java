package ru.pkg.web.dish;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pkg.model.Dish;
import java.net.URI;
import java.util.List;

import static ru.pkg.utils.constants.ControllerConstants.*;

@RestController
@RequestMapping(PATH_REST_DISH_LIST)
public class DishRestController extends AbstractDishController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@RequestBody Dish dish, @PathVariable(PATH_VAR_RESTAURANT_ID) int restaurantId) {
        Dish created = super.create(dish, restaurantId);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(PATH_REST_DISH_LIST + PATH_ID)
                    .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable(PATH_VAR_ID) int id, @RequestBody Dish dish, @PathVariable(PATH_VAR_RESTAURANT_ID) int restaurantId) {
        super.update(id, dish, restaurantId);
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish findById(@PathVariable(PATH_VAR_ID) int id, @PathVariable(PATH_VAR_RESTAURANT_ID) int restaurantId) {
        return super.findById(id, restaurantId);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> findAll(@PathVariable(PATH_VAR_RESTAURANT_ID) int restaurantId) {
        return super.findAll(restaurantId);
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.DELETE)
    public void delete(@PathVariable(PATH_VAR_ID) int id, @PathVariable(PATH_VAR_RESTAURANT_ID) int restaurantId) {
        super.delete(id, restaurantId);
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.POST)
    public void changeInMenuState(@PathVariable(PATH_VAR_ID) int id, @PathVariable(PATH_VAR_RESTAURANT_ID) int restaurantId) {
        super.changeInMenuState(id, restaurantId);
    }
}
