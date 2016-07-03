package ru.pkg.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pkg.service.RestaurantService;
import ru.pkg.service.UserService;
import ru.pkg.service.VotingService;

@Controller
public class RootController {

    @Autowired
    UserService userService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    VotingService votingService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String showHomePage() {
        return "redirect:restaurants";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model,
                        @RequestParam(value = "error", required = false) boolean error) {

        model.put("error", error);
        return "login";
    }

    @RequestMapping(path = "/admin/restaurants", method = RequestMethod.GET)
    public String showRestaurantTable() {
        return "restaurant/restaurantTable";
    }

    @RequestMapping(path = "/restaurants", method = RequestMethod.GET)
    public String showRestaurantCatalog() {
        return "restaurant/restaurantCatalog";
    }

    @RequestMapping(value = "/dishes", method = RequestMethod.GET)
    public String showDishList() {
        return "dish/dishList";
    }

    @RequestMapping(path="/users")
    public String showUserList() {
        return "user/userList";
    }
}
