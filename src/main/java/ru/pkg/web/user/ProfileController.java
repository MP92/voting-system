package ru.pkg.web.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pkg.LoggedUser;
import ru.pkg.to.UserTO;

import javax.validation.Valid;

@Controller
@SessionAttributes("user")
public class ProfileController extends AbstractUserController {

    @RequestMapping(path = "ajax/profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserTO get() {
        return LoggedUser.getUserTO();
    }

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
                result.rejectValue("name", "exception.user.duplicate_name");
            }
        }
        return "profile";
    }
}
