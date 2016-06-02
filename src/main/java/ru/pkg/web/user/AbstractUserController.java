package ru.pkg.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.User;
import ru.pkg.service.UserService;
import ru.pkg.to.UserTO;

import java.util.Collection;
import java.util.Objects;

public abstract class AbstractUserController {

    @Autowired
    private UserService service;

    public User get(int id) {
        return service.findById(id);
    }

    public void delete(int id) {
        service.delete(id);
    }

    public void create(UserTO user) {
        user.setId(null);
        service.add(user);
    }

    public void update(UserTO user) {
        Objects.requireNonNull(user.getId());
        service.update(user);
    }

    public Collection<User> findAll() {
        return service.findAll();
    }
}
