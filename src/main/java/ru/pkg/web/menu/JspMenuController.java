package ru.pkg.web.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pkg.model.Dish;
import ru.pkg.model.Menu;
import ru.pkg.service.DishService;

import java.util.List;

@Controller
@RequestMapping(path = "/restaurants/menu", method = RequestMethod.GET)
public class JspMenuController extends AbstractMenuController {

    @Autowired
    DishService dishService;

    @RequestMapping(path = "/form")
    public String initMenuForm(@RequestParam("restaurantId") int id, Model model) {
        List<Dish> dishList = dishService.findAll(id);

        model.addAttribute("dishList", dishList);
        model.addAttribute("holder", super.findById(id));

        return "restaurant/addToMenuForm";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String updateMenu(Menu menu) {
        super.update(menu.getRestaurantId(), menu);
        return "redirect:/restaurants/details?id=" + menu.getRestaurantId();
    }

    @RequestMapping("/delete")
    public String deleteFromMenu(@RequestParam("restaurantId") int restaurantId, @RequestParam("dishId") int dishId) {
        super.delete(restaurantId, dishId);
        return "redirect:/restaurants/details?id=" + restaurantId;
    }
}
