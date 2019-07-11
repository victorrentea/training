package ro.victor.unittest.time;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeProvider {
    private static LocalDateTime testTime = null;

    public static LocalDateTime currentTime() {
        if (testTime != null) {
            return testTime;
        } else {
            return LocalDateTime.now();
        }
    }

    public static LocalDate currentDate() {
        return currentTime().toLocalDate();
    }

    static void setTestTime(LocalDateTime testTime) {
        TimeProvider.testTime = testTime;
    }

    public static void clearTestTime() {
        testTime = null;
    }
}