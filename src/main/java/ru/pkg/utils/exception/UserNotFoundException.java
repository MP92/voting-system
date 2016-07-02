package ru.pkg.utils.exception;

import ru.pkg.model.User;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(User user) {
        this(user.getId());
    }

    public UserNotFoundException(int id) {
        super("User with id=" + id + " not found");
    }

    public UserNotFoundException(String name) {
        super("User with name=" + name + " not found");
    }
}
