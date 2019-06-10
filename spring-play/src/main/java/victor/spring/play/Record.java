package victor.spring.play;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Record {
    @MyConstraint
    private final String x;

    Record(String x) {
        this.x = x;
    }
}


