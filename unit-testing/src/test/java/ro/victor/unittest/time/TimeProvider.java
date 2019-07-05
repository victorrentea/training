package ro.victor.unittest.time;

import java.time.LocalDateTime;

public class TimeProvider {
       private static LocalDateTime testTime = null;

       public static LocalDateTime now() {
             if (testTime != null) {
                    return testTime;
             } else {
                    return LocalDateTime.now();
             }
       }

       static void setTestTimeStr(LocalDateTime testTime) {
             TimeProvider.testTime = testTime;
       }
       public static void clearTestTime() {
             testTime = null;
       }
}