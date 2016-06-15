package ru.pkg.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pkg.model.Dish;
import ru.pkg.service.RestaurantService;

import ru.pkg.utils.RestaurantUtil;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/dishes", method = RequestMethod.GET)
public class JspDishController extends AbstractDishController {

    @Autowired
    RestaurantService restaurantService;

    @RequestMapping
    public String showList(@RequestParam(value = "restaurantId", required = false) Integer restaurantId, Model model,
                           @RequestParam(value = "status", required = false) String status) {
        if (!StringUtils.isEmpty(status)) {
            model.addAttribute("status", status);
        }
        if (!StringUtils.isEmpty(restaurantId)) {
            model.addAttribute("dishList", super.findAll(restaurantId)).addAttribute("restaurantId", restaurantId);
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
    public String save(@Valid Dish dish, BindingResult result) {
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
        return "redirect:/dishes?restaurantId=" + restaurantId + "&status=" + (isNew ? "created" : "updated");
    }

    @RequestMapping("/delete")
    public String deleteDish(@RequestParam("id") int id, @RequestParam("restaurantId") int restaurantId) {
        super.delete(id, restaurantId);
        return "redirect:/dishes?restaurantId=" + restaurantId + "&status=deleted";
    }
}
