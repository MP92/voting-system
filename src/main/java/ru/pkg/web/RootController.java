package ru.pkg.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pkg.LoggedUser;
import ru.pkg.service.RestaurantService;
import ru.pkg.service.UserService;
import ru.pkg.service.VotingService;
import ru.pkg.to.UserTO;

import javax.validation.Valid;

import static ru.pkg.utils.EntityUtils.createFromTO;

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
                        @RequestParam(value = "error", required = false) boolean error,
                        @RequestParam(value = "message", required = false) String message) {

        if (LoggedUser.get() != null) {
            return "restaurant/restaurantCatalog";
        }

        model.put("error", error);
        model.put("message", message);
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

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String initUserRegisterForm(Model model) {
        model.addAttribute("user", new UserTO());
        return "profile";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("user") @Valid UserTO userTO, BindingResult result) {
        if (!result.hasErrors()) {
            try {
                userService.add(createFromTO(userTO));
                return "redirect:login?message=You have successfully signed up.";
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("name", null, "User with such name already present in application.");
            }
        }
        return "profile";
    }
}
