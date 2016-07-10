package ru.pkg.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class TimeUtil {
    private static LocalTime votingLimit = LocalTime.of(22, 0);

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
        return LocalTime.now().isBefore(votingLimit);
    }

    public static LocalTime getVotingLimit() {
        return votingLimit;
    }

    public static void setVotingLimit(LocalTime votingLimit) {
        TimeUtil.votingLimit = votingLimit;
    }
}
