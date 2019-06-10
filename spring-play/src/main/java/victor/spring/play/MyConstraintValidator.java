package victor.spring.play;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

    @Autowired
    Other other;


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        System.out.println("Other: " + other);
        return false;
    }
}

@Component
class Other {

}