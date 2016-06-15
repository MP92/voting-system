package ru.pkg.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.pkg.LoggedUser;
import ru.pkg.to.UserTO;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/profile")
public class JspProfileController extends AbstractUserController {

    @RequestMapping(method = RequestMethod.GET)
    public String showDetails(Model model) {
        model.addAttribute("user", super.get(LoggedUser.getId()));
        return "user/userDetails";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String initEditForm(Model model) {
        model.addAttribute("user", super.get(LoggedUser.getId()));
        return "user/userForm";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public String updateProfile(@Valid UserTO userTO, BindingResult result) {
        if (result.hasErrors()) {
            return "user/userForm";
        }

        userTO.setId(LoggedUser.getId());
        super.update(userTO);
        return "redirect:/restaurants";
    }
}
