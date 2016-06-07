package ru.pkg.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.pkg.LoggedUser;
import ru.pkg.to.UserTO;

@Controller
@RequestMapping(path = "/profile", method = RequestMethod.GET)
public class JspProfileController extends AbstractUserController {

    public String showDetails(Model model) {
        model.addAttribute("user", super.get(LoggedUser.getId()));
        return "user/userDetails";
    }

    @RequestMapping(path = "/edit")
    public String initEditForm(Model model) {
        model.addAttribute("user", super.get(LoggedUser.getId()));
        return "user/userForm";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public String updateProfile(UserTO userTO) {
        userTO.setId(LoggedUser.getId());
        super.update(userTO);
        return "redirect:/restaurants";
    }
}
