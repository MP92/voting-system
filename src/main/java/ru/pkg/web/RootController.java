package ru.pkg.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pkg.LoggedUser;
import ru.pkg.service.RestaurantService;
import ru.pkg.service.UserService;
import ru.pkg.service.VotingService;
import ru.pkg.utils.VotingUtil;

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
        return "index";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("userName") String userName) {
        if ("admin".equals(userName)) {
            LoggedUser.setId(10000);
        } else if ("user".equals(userName)) {
            LoggedUser.setId(10001);
        }
        return "redirect:/restaurants";
    }

    @RequestMapping(path = "admin/restaurants", method = RequestMethod.GET)
    public String showRestaurantTable() {
        return "restaurant/restaurantTable";
    }

    @RequestMapping(path = "/restaurants", method = RequestMethod.GET)
    public String showRestaurantCatalog() {
        return "restaurant/restaurantCatalog";
    }

    @RequestMapping(value = "admin/dishes", method = RequestMethod.GET)
    public String showDishList() {
        return "dish/dishList";
    }

    @RequestMapping(path="admin/users")
    public String showUserList() {
        return "user/userList";
    }
}
