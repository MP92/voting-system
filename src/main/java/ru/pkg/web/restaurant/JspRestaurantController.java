package ru.pkg.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pkg.model.Restaurant;
import ru.pkg.service.VotesService;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/restaurants", method = RequestMethod.GET)
public class JspRestaurantController extends AbstractRestaurantController {

    @Autowired
    VotesService votesService;

    @RequestMapping
    public String showList(@RequestParam(value = "votedId", required = false) Integer votedId,
                           @RequestParam(value = "status", required = false) String status, Model model) {

        if (!StringUtils.isEmpty(votedId)) {
            model.addAttribute("votedId", votedId);
        }
        if (!StringUtils.isEmpty(status)) {
            model.addAttribute("status", status);
        }
        model.addAttribute("restaurantList", super.findAll()).addAttribute("votesList", votesService.findAll());
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
    public String save(@Valid Restaurant restaurant, BindingResult result) {

        if (result.hasErrors()) {
            return "restaurant/restaurantForm";
        }

        boolean isNew = restaurant.isNew();
        if (isNew) {
            super.create(restaurant);
        } else {
            super.update(restaurant.getId(), restaurant);
        }
        return "redirect:/restaurants?status=" + (isNew ? "created" : "updated");
    }

    @RequestMapping("/delete")
    public String deleteRestaurant(@RequestParam("id") int id) {
        super.delete(id);
        return "redirect:/restaurants?status=deleted";
    }
}
