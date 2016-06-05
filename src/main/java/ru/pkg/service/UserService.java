package ru.pkg.service;

import ru.pkg.model.User;
import ru.pkg.to.UserTO;
import ru.pkg.utils.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    User add(User user);

    User findById(int id) throws UserNotFoundException;

    List<User> findAll();

    void update(User user) throws UserNotFoundException;

    void update(UserTO to) throws UserNotFoundException;

    void delete(int id) throws UserNotFoundException;

    void markAsVotedToday(int id);
}
