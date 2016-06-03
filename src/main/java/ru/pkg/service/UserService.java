package ru.pkg.service;

import ru.pkg.model.User;
import ru.pkg.to.UserTO;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.Collection;

public interface UserService {
    User findById(int id) throws UserNotFoundException;

    User add(User user);

    void update(User user) throws UserNotFoundException;

    void update(UserTO to) throws UserNotFoundException;

    void markAsVotedToday(int id);

    void delete(int id) throws UserNotFoundException;

    Collection<User> findAll();
}
