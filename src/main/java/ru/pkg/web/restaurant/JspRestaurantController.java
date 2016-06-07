package ru.pkg.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pkg.model.Dish;
import ru.pkg.model.Restaurant;
import ru.pkg.service.DishService;

import java.util.List;

import ru.pkg.utils.DishUtil;

@Controller
@RequestMapping(path = "/restaurants", method = RequestMethod.GET)
public class JspRestaurantController extends AbstractRestaurantController {

    @Autowired
    DishService dishService;

    @RequestMapping
    public String showList(@RequestParam(value = "votedId", required = false) Integer votedId,
                           @RequestParam(value = "status", required = false) String status, Model model) {
        if (!StringUtils.isEmpty(votedId)) {
            model.addAttribute("votedId", votedId);
        }
        if (!StringUtils.isEmpty(status)) {
            model.addAttribute("status", status);
        }
        model.addAttribute("restaurantList", super.findAll());
        return "restaurant/restaurantList";
    }

    @RequestMapping("/details")
    public String showDetails(@RequestParam("id") int id, Model model) {
        model.addAttribute("restaurant", super.findById(id));
        return "restaurant/restaurantDetails";
    }

    @RequestMapping("/edit")
    public String initEditForm(@RequestParam("id") int id, Model model) {
        model.addAttribute("restaurant", super.findById(id));
        return "restaurant/restaurantForm";
    }

    @RequestMapping("/add")
    public String initCreateForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "restaurant/restaurantForm";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String save(Restaurant restaurant) {
        boolean isNew = restaurant.isNew();
        if (isNew) {
            super.create(restaurant);
        } else {
            super.update(restaurant);
        }
        return "redirect:/restaurants?status=" + (isNew ? "created" : "updated");
    }

    @RequestMapping("/delete")
    public String deleteRestaurant(@RequestParam("id") int id) {
        super.delete(id);
        return "redirect:/restaurants?status=deleted";
    }

    @RequestMapping("/resetVotes")
    public String resetRestaurantsVotes() {
        super.resetVotes();
        return "redirect:/restaurants";
    }

    @RequestMapping(path = "/menu/form")
    public String initMenuForm(@RequestParam("restaurantId") int id, Model model) {
        List<Dish> dishList = dishService.findAll(id);

        model.addAttribute("dishList", dishList);
        model.addAttribute("holder", new DishIDsHolder(id, DishUtil.getIDs(dishService.findMenu(id))));

        return "restaurant/addToMenuForm";
    }

    @RequestMapping(path = "/menu/update", method = RequestMethod.POST)
    public String updateMenu(DishIDsHolder holder) {
        int restaurantId = holder.getRestaurantId();
        Restaurant restaurant = super.findById(restaurantId);
        List<Dish> menu = DishUtil.getFilteredByIDs(dishService.findAll(restaurantId), holder.getDishIDs());
        restaurant.setMenu(menu);
        super.update(restaurant);
        return "redirect:/restaurants/details?id=" + restaurantId;
    }

    @RequestMapping("/menu/delete")
    public String deleteFromMenu(@RequestParam("restaurantId") int id, @RequestParam("dishId") int dishId) {
        super.deleteDishFromMenu(id, dishId);
        return "redirect:/restaurants/details?id=" + id;
    }
}
