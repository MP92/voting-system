package ru.pkg.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class TimeUtil {
    private static final LocalTime HOUR_LIMIT = LocalTime.of(22, 0);

    private TimeUtil() {
    }

    public static LocalDateTime parse(String ldt) {
        return ldt != null ? LocalDateTime.parse(ldt) : null;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt.toString();
    }

    public static boolean isToday(LocalDateTime dateTime) {
        return dateTime != null && dateTime.compareTo(LocalDate.now().atStartOfDay()) >= 0;
    }

    public static boolean isCanVote() {
        return LocalTime.now().isBefore(HOUR_LIMIT);
    }
}
