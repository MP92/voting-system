package ru.pkg.service;

import ru.pkg.model.User;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Collection;

public interface UserService {
    User findById(int id) throws UserNotFoundException;

    void add(User user);

    void update(User user) throws UserNotFoundException;

    void delete(int id) throws UserNotFoundException;

    Collection<User> findAll();
}
