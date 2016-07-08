package ru.pkg.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.User;
import ru.pkg.service.UserService;
import ru.pkg.to.UserTO;

import java.util.Collection;

import static ru.pkg.utils.EntityUtils.*;

public abstract class AbstractUserController {

    @Autowired
    private UserService service;

    protected User get(int id) {
        return service.findById(id);
    }

    protected void delete(int id) {
        service.delete(id);
    }

    protected User create(User user) {
        user.setId(null);
        return service.add(prepareToSave(user));
    }

    protected void update(int id, User user) {
        user.setId(id);
        service.update(prepareToSave(user));
    }

    protected void update(UserTO user) {
        service.update(user);
    }

    protected Collection<User> findAll() {
        return service.findAll();
    }

    protected void changeEnabledState(int id) {
        service.changeEnabledState(id);
    }
}
