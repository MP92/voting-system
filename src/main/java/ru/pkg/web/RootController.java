package ru.pkg.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import ru.pkg.LoggedUser;
import ru.pkg.service.UserService;
import ru.pkg.to.UserTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.Locale;
import java.util.ResourceBundle;

import static ru.pkg.utils.EntityUtils.createFromTO;

@Controller
public class RootController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    UserService userService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String showHomePage() {
        return "redirect:restaurants";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model,
                        @RequestParam(value = "error", required = false) boolean error) {

        if (LoggedUser.get() != null) {
            return "restaurant/restaurantCatalog";
        }

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

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String initUserRegisterForm(Model model) {
        model.addAttribute("user", new UserTO());
        return "profile";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("user") @Valid UserTO userTO, BindingResult result, RedirectAttributes redirectAttrs) {
        if (!result.hasErrors()) {
            try {
                userService.add(createFromTO(userTO));
                redirectAttrs.addFlashAttribute("message", messageSource.getMessage("login.register.success", null, LocaleContextHolder.getLocale()));
                return "redirect:login";
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("name", "exception.user.duplicate_name");
            }
        }
        return "profile";
    }

    @RequestMapping(value="messages.js")
    public ModelAndView localizedMessages() {
        // Retrieve the locale of the User
        Locale locale = LocaleContextHolder.getLocale();
        // Use the path to your bundle
        ResourceBundle bundle = ResourceBundle.getBundle("messages.js", locale);
        // Call the string.jsp view
        return new ModelAndView("fragments/messages", "keys", bundle.getKeys());
    }
}
