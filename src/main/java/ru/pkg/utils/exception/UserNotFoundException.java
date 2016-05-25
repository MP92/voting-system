package ru.pkg.utils.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {
        super("User with id=" + id + " not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
    }
}
