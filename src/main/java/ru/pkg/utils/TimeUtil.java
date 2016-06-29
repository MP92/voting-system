package ru.pkg.utils;

import java.time.LocalDateTime;

public class TimeUtil {

    public static LocalDateTime parse(String ldt) {
        return ldt != null ? LocalDateTime.parse(ldt) : null;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt.toString();
    }

    public static boolean isToday(LocalDateTime dateTime) {
        return dateTime != null && dateTime.compareTo(dateTime.toLocalDate().atStartOfDay()) >= 0;
    }
}
