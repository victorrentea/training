package ro.victor.unittest.time;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestTimeRuleTest.WithHackedTime.class, TestTimeRuleTest.WithoutHackedTime.class})
public class TestTimeRuleTest {

    public static LocalDateTime getTimeFromProd() {
        return TimeProvider.currentTime();
    }

    public static class WithHackedTime {
        @Rule
        public TestTimeRule timeRule = new TestTimeRule(LocalDateTime.of(2019, 01, 01, 0, 0));

        @Test
        public void testWithHackedTime() {
            assertEquals(timeRule.getTestTime(), getTimeFromProd());
        }
    }
    public static class WithoutHackedTime {
        @Test
        public void testWithoutTimeHack() {
            assertEquals(
                    LocalDateTime.now().truncatedTo(ChronoUnit.DAYS),
                    getTimeFromProd().truncatedTo(ChronoUnit.DAYS));
        }
    }
}