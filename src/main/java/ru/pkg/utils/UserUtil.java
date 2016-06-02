package ru.pkg.utils;

import ru.pkg.model.Role;
import ru.pkg.model.User;
import ru.pkg.to.UserTO;

public class UserUtil {
    public static User createFromTO(UserTO userTO) {
        return new User(userTO.getId(), userTO.getName(), userTO.getSurname(), userTO.getPassword(), Role.ROLE_USER);
    }

    public static User updateFromTO(User user, UserTO userTO) {
        user.setName(userTO.getName());
        user.setSurname(userTO.getSurname());
        user.setPassword(userTO.getPassword());
        return user;
    }
}
