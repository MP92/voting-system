package ru.pkg.web.dish;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.pkg.model.Dish;
import ru.pkg.to.DishTO;
import ru.pkg.utils.DishUtil;
import ru.pkg.utils.exception.DishNotFoundException;
import ru.pkg.web.restaurant.RestaurantAjaxController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(RestaurantAjaxController.AJAX_URL + "/{restaurantId}/dishes")
public class DishAjaxController extends AbstractDishController {

    @RequestMapping(method = RequestMethod.POST)
    public void save(@Valid DishTO dishTO, @PathVariable("restaurantId") int restaurantId) throws DishNotFoundException {
        try {
            if (dishTO.isNew()) {
                super.create(DishUtil.createFromTO(dishTO), restaurantId);
            } else {
                super.update(DishUtil.createFromTO(dishTO), restaurantId);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Dish with such name already present in application.");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish findById(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) throws DishNotFoundException {
        return super.findById(id, restaurantId);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> findAll(@PathVariable("restaurantId") int restaurantId) {
        return super.findAll(restaurantId);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) throws DishNotFoundException {
        super.delete(id, restaurantId);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
    public void changeInMenuState(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        super.changeInMenuState(id, restaurantId);
    }
}
