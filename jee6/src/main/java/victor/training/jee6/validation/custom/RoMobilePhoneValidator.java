package victor.training.jee6.validation.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoMobilePhoneValidator implements ConstraintValidator<RoMobilePhone, String> {

	@Override
	public void initialize(RoMobilePhone constraintAnnotation) {
		System.out.println("CustomValidator: Init");
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		System.out.println("CustomValidator: Check isValid");
		return value.matches("(\\+4)?07\\d{8}");
	}

}
