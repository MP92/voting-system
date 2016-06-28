package ru.pkg.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.pkg.model.Restaurant;
import ru.pkg.utils.exception.RestaurantNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(RestaurantAjaxController.AJAX_URL)
public class RestaurantAjaxController extends AbstractRestaurantController {

    public static final String AJAX_URL = "/ajax/restaurants";

    @RequestMapping(method = RequestMethod.POST)
    public void save(@Valid Restaurant restaurant) {
        if (restaurant.isNew()) {
            super.create(restaurant);
        } else {
            super.update(restaurant.getId(), restaurant);
        }
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
