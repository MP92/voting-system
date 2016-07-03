package ru.pkg;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.pkg.model.User;
import ru.pkg.to.UserTO;
import ru.pkg.utils.UserUtil;

public class LoggedUser extends org.springframework.security.core.userdetails.User {

    private UserTO userTO;

    public LoggedUser(User u) {
        super(u.getName(), u.getPassword(), u.isEnabled(), true, true, true, u.getRoles());
        userTO = UserUtil.asTO(u);
    }

    public static LoggedUser get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        return (LoggedUser)auth.getPrincipal();
    }

    public static UserTO getUserTO() {
        return get().userTO;
    }

    public static int getId() {
        return get().userTO.getId();
    }

    public static void update(UserTO userTO) {
        get().userTO = userTO;
    }
}
