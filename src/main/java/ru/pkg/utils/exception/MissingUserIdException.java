package ru.pkg.utils.exception;

import ru.pkg.model.User;

public class MissingUserIdException extends RuntimeException {

    public MissingUserIdException(User user) {
        super("Missing id of user " + user);
    }
}
