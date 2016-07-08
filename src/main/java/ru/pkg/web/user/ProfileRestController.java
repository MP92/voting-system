package ru.pkg.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.pkg.LoggedUser;
import ru.pkg.model.User;

import static ru.pkg.utils.constants.ControllerConstants.*;

@RestController
@RequestMapping(PATH_REST_PROFILE)
public class ProfileRestController extends AbstractUserController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(LoggedUser.getId());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete() {
        super.delete(LoggedUser.getId());
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        super.update(LoggedUser.getId(), user);
    }
}