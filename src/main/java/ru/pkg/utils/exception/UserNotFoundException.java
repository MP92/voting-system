package ru.pkg.utils.exception;

import ru.pkg.model.User;

public class UserNotFoundException extends RuntimeException {

    private static final String MSG_PATTERN = "User with id=%d not found";

    public UserNotFoundException(User user) {
        this(user.getId());
    }

    public UserNotFoundException(int id) {
        super(String.format(MSG_PATTERN, id));
    }
}
