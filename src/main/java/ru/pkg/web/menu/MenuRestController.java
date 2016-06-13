package ru.pkg.web.menu;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.pkg.model.Menu;

import java.util.List;

@RestController
@RequestMapping("/rest/restaurants")
public class MenuRestController extends AbstractMenuController {

    @RequestMapping(path = "/{restaurantId}/menu",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Menu findById(@PathVariable("restaurantId") int restaurantId) {
        return super.findById(restaurantId);
    }

    @RequestMapping(path = "/menus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Menu> findAll() {
        return super.findAll();
    }

    @RequestMapping(path = "/{restaurantId}/menu", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@PathVariable("restaurantId") int restaurantId, @RequestBody Menu menu) {
        super.update(restaurantId, menu);
    }

    @RequestMapping(path = "/{restaurantId}/menu/{dishId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) {
        super.delete(restaurantId, dishId);
    }
}
