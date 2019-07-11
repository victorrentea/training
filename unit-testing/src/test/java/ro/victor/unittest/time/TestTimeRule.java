package ro.victor.unittest.time;

import org.junit.rules.ExternalResource;

import java.time.LocalDateTime;

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

    public LocalDateTime getTestTime() {
        return testTime;
    }

    @Override
    protected void after() {
        TimeProvider.clearTestTime();
    }
}