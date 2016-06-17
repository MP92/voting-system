package ru.pkg.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.pkg.model.Dish;
import ru.pkg.service.RestaurantService;

import ru.pkg.utils.RestaurantUtil;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/dishes", method = RequestMethod.GET)
public class JspDishController extends AbstractDishController {

    private static final String MESSAGE_FORMAT = "Dish for restaurant with id=%d has been %s";

    @Autowired
    RestaurantService restaurantService;

    @RequestMapping
    public String showList(@RequestParam(value = "restaurantId", required = false) Integer restaurantId, Model model) {
        if (restaurantId != null) {
            model.addAttribute("restaurantId", restaurantId).addAttribute("dishList", super.findAll(restaurantId));
        }

        model.addAttribute("restaurantIDs", RestaurantUtil.getIDs(restaurantService.findAll()));
        return "dish/dishList";
    }

    @RequestMapping("/edit")
    public String initEditForm(@RequestParam("id") int id, @RequestParam("restaurantId") int restaurantId, Model model) {
        model.addAttribute(super.findById(id, restaurantId));
        return "dish/dishForm";
    }

    @RequestMapping("/add")
    public String initCreateForm(@RequestParam("restaurantId") int restaurantId, Model model) {
        model.addAttribute(new Dish(restaurantId));
        return "dish/dishForm";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String save(@Valid Dish dish, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "dish/dishForm";
        }

        int restaurantId = dish.getRestaurantId();
        boolean isNew = dish.isNew();
        if (isNew) {
            super.create(dish, restaurantId);
        } else {
            super.update(dish, restaurantId);
        }
        attributes.addFlashAttribute("message", String.format(MESSAGE_FORMAT, restaurantId, isNew ? "created" : "updated"));
        return "redirect:/dishes?restaurantId=" + restaurantId;
    }

    @RequestMapping("/delete")
    public String deleteDish(@RequestParam("id") int id, @RequestParam("restaurantId") int restaurantId, RedirectAttributes attributes) {
        super.delete(id, restaurantId);
        attributes.addFlashAttribute("message", String.format(MESSAGE_FORMAT, restaurantId, "deleted"));
        return "redirect:/dishes?restaurantId=" + restaurantId;
    }
}
