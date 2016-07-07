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

import static ru.pkg.utils.constants.ControllerConstants.*;

@Controller
@SessionAttributes(ATTRIBUTE_USER)
public class ProfileController extends AbstractUserController {

    @RequestMapping(value = PATH_AJAX_PROFILE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserTO get() {
        return LoggedUser.getUserTO();
    }

    @RequestMapping(value = PATH_PROFILE, method = RequestMethod.GET)
    public String show(Model model) {
        model.addAttribute(ATTRIBUTE_USER, LoggedUser.getUserTO());
        return PATH_PROFILE;
    }

    @RequestMapping(value = PATH_PROFILE, method = RequestMethod.POST)
    public String update(@ModelAttribute(ATTRIBUTE_USER) @Valid UserTO userTO, BindingResult result) {
        if (!result.hasErrors()) {
            try {
                userTO.setId(LoggedUser.getId());
                super.update(userTO);
                LoggedUser.update(userTO);
                return redirectTo(PATH_RESTAURANT_CATALOG);
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("name", "exception.user.duplicate_name");
            }
        }
        return PATH_PROFILE;
    }
}
