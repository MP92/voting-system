package ru.pkg.utils.exception;

public class UserModificationDeniedException extends RuntimeException {
    public UserModificationDeniedException() {
    }

    public UserModificationDeniedException(String message) {
        super(message);
    }
}