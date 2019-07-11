package ro.victor.unittest.time;

import org.junit.rules.ExternalResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestTimeRule extends ExternalResource {

    private LocalDateTime testTime;

    public TestTimeRule() {
        this(LocalDateTime.now());
    }

    public TestTimeRule(LocalDateTime testTime) {
        this.testTime = testTime;
        TimeProvider.setTestTime(testTime);
    }

    public void setTestTime(LocalDateTime testTime) {
        this.testTime = testTime;
    }
    public void setTestDate(LocalDate testDate) {
        this.testTime = LocalDateTime.of(testDate, LocalTime.MIDNIGHT);
    }

    public LocalDateTime getTestTime() {
        return testTime;
    }

    @Override
    protected void after() {
        TimeProvider.clearTestTime();
    }
}