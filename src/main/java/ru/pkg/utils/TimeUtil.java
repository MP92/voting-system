package ru.pkg.utils;

import ru.pkg.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

    public static LocalDateTime parse(String ldt) {
        return LocalDateTime.parse(ldt);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt.toString();
    }

    public static boolean isUserVotedJustNow(User user) {
        return ChronoUnit.MILLIS.between(user.getLastVoted(), LocalDateTime.now()) < 500;
    }
}
