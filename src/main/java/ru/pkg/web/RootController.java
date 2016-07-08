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
import ru.pkg.LoggedUser;
import ru.pkg.service.UserService;
import ru.pkg.to.UserTO;
import javax.validation.Valid;
import java.util.ResourceBundle;

import static ru.pkg.utils.EntityUtils.createFromTO;
import static ru.pkg.utils.constants.ControllerConstants.*;

@Controller
public class RootController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage() {
        return redirectTo(PATH_RESTAURANT_CATALOG);
    }

    @RequestMapping(value = PATH_LOGIN, method = RequestMethod.GET)
    public String login(ModelMap model,
                        @RequestParam(value = PARAM_ERROR, required = false) boolean error) {

        if (LoggedUser.get() != null) {
            return VIEW_NAME_RESTAURANT_CATALOG;
        }

        model.put(PARAM_ERROR, error);
        return VIEW_NAME_LOGIN;
    }

    @RequestMapping(value = PATH_RESTAURANT_TABLE, method = RequestMethod.GET)
    public String showRestaurantTable() {
        return VIEW_NAME_RESTAURANT_TABLE;
    }

    @RequestMapping(value = PATH_RESTAURANT_CATALOG, method = RequestMethod.GET)
    public String showRestaurantCatalog() {
        return VIEW_NAME_RESTAURANT_CATALOG;
    }

    @RequestMapping(value = PATH_DISH_LIST, method = RequestMethod.GET)
    public String showDishList() {
        return VIEW_NAME_DISH_LIST;
    }

    @RequestMapping(value = PATH_USER_LIST)
    public String showUserList() {
        return VIEW_NAME_USER_LIST;
    }

    @RequestMapping(value = PATH_REGISTER, method = RequestMethod.GET)
    public String initUserRegisterForm(Model model) {
        model.addAttribute(ATTRIBUTE_USER, new UserTO());
        return VIEW_NAME_PROFILE;
    }

    @RequestMapping(value = PATH_REGISTER, method = RequestMethod.POST)
    public String createUser(@ModelAttribute(ATTRIBUTE_USER) @Valid UserTO userTO, BindingResult result, RedirectAttributes redirectAttrs) {
        if (!result.hasErrors()) {
            try {
                userService.add(createFromTO(userTO));
                redirectAttrs.addFlashAttribute("message", messageSource.getMessage("login.register.success", null, LocaleContextHolder.getLocale()));
                return redirectTo(PATH_LOGIN);
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("name", "exception.user.duplicate_name");
            }
        }
        return VIEW_NAME_PROFILE;
    }

    @RequestMapping(value = "messages.js")
    public ModelAndView localizedMessages() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages.js", LocaleContextHolder.getLocale());
        return new ModelAndView("fragments/messages", "keys", bundle.getKeys());
    }
}
