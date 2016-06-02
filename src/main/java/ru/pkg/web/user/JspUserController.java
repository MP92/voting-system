package ru.pkg.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.pkg.to.UserTO;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/users")
public class JspUserController extends AbstractUserController {

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") int id, ModelMap modelMap) {
        super.delete(id);
        modelMap.addAttribute("status", "deleted");
        return "redirect:/users";
    }

    @RequestMapping(path="/create", method = RequestMethod.GET)
    public String create(ModelMap modelMap) {
        modelMap.addAttribute("user", new UserTO());
        return "userForm";
    }

    @RequestMapping(path="/update", method = RequestMethod.GET)
    public String update(@RequestParam("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("user", super.get(id));
        return "userForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(@ModelAttribute UserTO userTO, ModelMap modelMap) {
        if (userTO.isNew()) {
            super.create(userTO);
        } else {
            super.update(userTO);
        }
        modelMap.addAttribute("status", userTO.isNew() ? "created" : "updated");

        return "redirect:/users";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(HttpServletRequest req, ModelMap modelMap) {
        String status = req.getParameter("status");
        if (!StringUtils.isEmpty(status)) {
            modelMap.put("status", status);
        }
        modelMap.addAttribute("userList", super.findAll());
        return "userList";
    }
}
