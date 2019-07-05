package ro.victor.unittest.time;

import org.junit.rules.ExternalResource;

import java.time.LocalDateTime;

public class TestTimeRule extends ExternalResource {

       private final LocalDateTime testTime;

       public TestTimeRule(LocalDateTime testTime) {
             this.testTime = testTime;
             TimeProvider.setTestTimeStr(testTime);
       }

       public LocalDateTime getTestTime() {
             return testTime;
       }

       @Override
       protected void after() {
             TimeProvider.clearTestTime();
       }
}