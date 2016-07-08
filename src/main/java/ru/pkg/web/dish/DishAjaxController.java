package ru.pkg.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.pkg.model.Dish;
import ru.pkg.to.DishTO;
import ru.pkg.web.exception.ErrorInfoExceptionHandler;
import javax.validation.Valid;
import java.util.List;

import static ru.pkg.utils.EntityUtils.*;
import static ru.pkg.utils.constants.ControllerConstants.*;

@RestController
@RequestMapping(PATH_AJAX_DISH_LIST)
public class DishAjaxController extends AbstractDishController implements ErrorInfoExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    public void save(@Valid DishTO dishTO, @PathVariable(PATH_VAR_RESTAURANT_ID) int restaurantId) {
        try {
            if (dishTO.isNew()) {
                super.create(createFromTO(dishTO), restaurantId);
            } else {
                super.update(createFromTO(dishTO), restaurantId);
            }
        } catch (DataIntegrityViolationException e) {
            String message = messageSource.getMessage("exception.dish.duplicate_name", null, LocaleContextHolder.getLocale());
            throw new DataIntegrityViolationException(message);
        }
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
