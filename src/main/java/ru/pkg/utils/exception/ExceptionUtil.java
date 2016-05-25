package ru.pkg.utils.exception;

public class ExceptionUtil {

    public static <T> T checkAndReturn(T object, int id) {
        if (object == null) {
            throw new UserNotFoundException(id);
        }
        return object;
    }

    public static void check(boolean result, int id) {
        if (!result) {
            throw new UserNotFoundException(id);
        }
    }
}
