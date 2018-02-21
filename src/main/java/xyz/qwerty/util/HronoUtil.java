package xyz.qwerty.util;

import java.time.LocalTime;


public final class HronoUtil {
    private HronoUtil() {}
    
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }
}
