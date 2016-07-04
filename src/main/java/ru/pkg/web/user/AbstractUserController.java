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

    public User get(int id) {
        return service.findById(id);
    }

    public UserTO getForUpdate(int id) {
        return asTO(service.findById(id));
    }

    public void delete(int id) {
        service.delete(id);
    }

    public User create(User user) {
        user.setId(null);
        return service.add(prepareToSave(user));
    }

    public void update(int id, User user) {
        user.setId(id);
        service.update(prepareToSave(user));
    }

    public void update(UserTO user) {
        service.update(user);
    }

    public Collection<User> findAll() {
        return service.findAll();
    }

    public void changeEnabledState(int id) {
        service.changeEnabledState(id);
    }
}
