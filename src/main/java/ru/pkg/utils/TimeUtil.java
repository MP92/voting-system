package ru.pkg.utils;

import java.time.LocalDateTime;

public class TimeUtil {

    public static LocalDateTime parse(String ldt) {
        return LocalDateTime.parse(ldt);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt.toString();
    }
}
