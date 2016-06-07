package ru.pkg.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.pkg.to.UserTO;

import static ru.pkg.utils.UserUtil.*;

@Controller
@RequestMapping(path = "/admin", method = RequestMethod.GET)
public class JspAdminController extends AbstractUserController {

    @RequestMapping(path="/save", method = RequestMethod.POST)
    public String saveUser(UserTO userTO) {
        if (userTO.isNew()) {
            super.create(createFromTO(userTO));
        } else {
            super.update(userTO);
        }

        return "redirect:/admin/users?status=" + (userTO.isNew() ? "created" : "updated");
    }

    @RequestMapping(path = "/delete")
    public String deleteUser(@RequestParam("id") int id, Model model) {
        super.delete(id);
        return "redirect:/admin/users?status=deleted";
    }

    @RequestMapping(path="/add")
    public String initUserCreateForm(Model model) {
        model.addAttribute("user", new UserTO());
        return "user/userForm";
    }

    @RequestMapping(path="/edit")
    public String initUserEditForm(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", super.get(id));
        return "user/userForm";
    }

    @RequestMapping(path="/details")
    public String showUserDetails(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", super.get(id));
        return "user/userDetails";
    }

    @RequestMapping(path="/users")
    public String showUserList(@RequestParam(value = "status", required = false) String status, Model model) {
        if (!StringUtils.isEmpty(status)) {
            model.addAttribute("status", status);
        }
        model.addAttribute("userList", super.findAll());
        return "user/userList";
    }
}
