package ru.pkg.web.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.pkg.model.Restaurant;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/restaurants", method = RequestMethod.GET)
public class JspRestaurantController extends AbstractRestaurantController {

    private static final String MESSAGE_FORMAT = "Restaurant with id=%d has been %s";

    @RequestMapping
    public String showList(Model model) {
        model.addAttribute("restaurantList", super.findAll()).addAttribute("votingStatistics", service.findStatistics());
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
    public String save(@Valid Restaurant restaurant, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "restaurant/restaurantForm";
        }

        boolean isNew = restaurant.isNew();
        if (isNew) {
            super.create(restaurant);
        } else {
            super.update(restaurant.getId(), restaurant);
        }
        attributes.addFlashAttribute("message", String.format(MESSAGE_FORMAT, restaurant.getId(), isNew ? "created" : "updated"));
        return "redirect:/restaurants";
    }

    @RequestMapping("/delete")
    public String deleteRestaurant(@RequestParam("id") int id, RedirectAttributes attributes) {
        super.delete(id);
        attributes.addFlashAttribute("message", String.format(MESSAGE_FORMAT, id, "deleted"));
        return "redirect:/restaurants";
    }
}
