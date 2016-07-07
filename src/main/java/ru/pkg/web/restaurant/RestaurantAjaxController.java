package ru.pkg.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.pkg.model.Restaurant;
import ru.pkg.to.RestaurantTO;
import javax.validation.Valid;
import java.util.List;

import static ru.pkg.utils.EntityUtils.*;
import static ru.pkg.utils.constants.ControllerConstants.*;

@RestController
@RequestMapping(PATH_AJAX_RESTAURANT_LIST)
public class RestaurantAjaxController extends AbstractRestaurantController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    public void save(@Valid RestaurantTO restaurantTO) {
        try {
            if (restaurantTO.isNew()) {
                super.create(createFromTO(restaurantTO));
            } else {
                super.update(restaurantTO.getId(), createFromTO(restaurantTO));
            }
        } catch (DataIntegrityViolationException e) {
            String message = messageSource.getMessage("exception.restaurant.duplicate_name", null, LocaleContextHolder.getLocale());
            throw new DataIntegrityViolationException(message);
        }
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant findById(@PathVariable(PATH_VAR_ID) int id) {
        return super.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> findAll() {
        return super.findAll();
    }

    @RequestMapping(value = PATH_ID, method = RequestMethod.DELETE)
    public void delete(@PathVariable(PATH_VAR_ID) int id) {
        super.delete(id);
    }
}