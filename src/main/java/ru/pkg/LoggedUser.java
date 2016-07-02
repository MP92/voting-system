package ru.pkg;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.pkg.model.User;

public class LoggedUser extends org.springframework.security.core.userdetails.User {

    private static int id = 10001;

    public LoggedUser(User u) {
        super(u.getName(), u.getPassword(), u.isEnabled(), true, true, true, u.getRoles());
    }

    public static LoggedUser get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        return (LoggedUser)auth.getPrincipal();
    }

    public static int getId() {
        return id;
    }
}
