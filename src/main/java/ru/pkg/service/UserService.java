package ru.pkg.service;

import ru.pkg.model.User;
import ru.pkg.to.UserTO;
import java.util.List;

public interface UserService {

    User add(User user);

    User findById(int id);

    User findByName(String name);

    List<User> findAll();

    void update(User user);

    void update(UserTO to);

    void delete(int id);

    void changeEnabledState(int id);
}
