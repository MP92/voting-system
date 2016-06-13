package ru.pkg.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pkg.LoggedUser;
import ru.pkg.service.UserService;

@Controller
public class RootController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String showHomePage() {
        return "index";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("userName") String userName) {
        if ("admin".equals(userName)) {
            LoggedUser.setId(1);
        } else if ("user".equals(userName)) {
            LoggedUser.setId(2);
        }
        return "redirect:/restaurants";
    }
}
