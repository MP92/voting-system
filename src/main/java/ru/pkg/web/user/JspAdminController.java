package ru.pkg.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.pkg.model.User;
import ru.pkg.to.UserTO;
import ru.pkg.utils.exception.UserNotFoundException;

import javax.validation.Valid;

import static ru.pkg.utils.UserUtil.*;

@Controller
@RequestMapping(path = "/admin/users", method = RequestMethod.GET)
public class JspAdminController extends AbstractUserController {

    public static final String MESSAGE_FORMAT = "User with id=%d %s";

    @RequestMapping(path="/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") @Valid UserTO userTO, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "user/userForm";
        }

        boolean isNew = userTO.isNew();
        if (isNew) {
            User created = super.create(createFromTO(userTO));
            userTO.setId(created.getId());
        } else {
            try {
                super.update(userTO);
            } catch (UserNotFoundException e) {
                result.rejectValue("id", "User with the specified id not found");
                return "user/userForm";
            }
        }
        attributes.addFlashAttribute("message", String.format(MESSAGE_FORMAT, userTO.getId(), "has been " + (isNew ? "created" : "updated")));

        return "redirect:/admin/users";
    }

    @RequestMapping(path = "/delete")
    public String deleteUser(@RequestParam("id") int id, RedirectAttributes attributes) {
        try {
            super.delete(id);
            attributes.addFlashAttribute("message", String.format(MESSAGE_FORMAT, id, "has been deleted"));
        } catch (UserNotFoundException e) {
            attributes.addFlashAttribute("message", String.format(MESSAGE_FORMAT, id, "not found"));
        }
        return "redirect:/admin/users";
    }

    @RequestMapping(path="/add")
    public String initUserCreateForm(Model model) {
        model.addAttribute("user", new UserTO());
        return "user/userForm";
    }

    @RequestMapping(path="/edit")
    public String initUserEditForm(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", super.getForUpdate(id));
        return "user/userForm";
    }

    @RequestMapping(path="/details")
    public String showUserDetails(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", super.get(id));
        return "user/userDetails";
    }

    @RequestMapping
    public String showUserList(Model model) {
        model.addAttribute("userList", super.findAll());
        return "user/userList";
    }
}
