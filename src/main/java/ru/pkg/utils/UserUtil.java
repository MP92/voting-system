package ru.pkg.utils;

import ru.pkg.model.Role;
import ru.pkg.model.User;
import ru.pkg.to.UserTO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    public static UserTO asTO(User user) {
        return new UserTO(user.getId(), user.getName(), user.getSurname(), user.getPassword(), user.getLastVoted());
    }

    public static boolean isUserVotedJustNow(User user) {
        return ChronoUnit.MILLIS.between(user.getLastVoted(), LocalDateTime.now()) < 500;
    }
}
