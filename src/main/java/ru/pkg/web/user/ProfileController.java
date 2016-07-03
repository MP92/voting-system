package ru.pkg.web.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.pkg.LoggedUser;
import ru.pkg.to.UserTO;

import javax.validation.Valid;

@Controller
public class ProfileController extends AbstractUserController {

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String show(Model model) {
        model.addAttribute("user", LoggedUser.getUserTO());
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String update(@ModelAttribute("user") @Valid UserTO userTO, BindingResult result) {
        if (!result.hasErrors()) {
            try {
                userTO.setId(LoggedUser.getId());
                super.update(userTO);
                LoggedUser.update(userTO);
                return "redirect:restaurants";
            } catch (DataIntegrityViolationException ex) {
                result.reject("name", null, "User with such name already present in application.");
            }
        }
        return "profile";
    }
}
