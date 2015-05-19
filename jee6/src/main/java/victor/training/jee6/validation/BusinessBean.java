package victor.training.jee6.validation;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

public class BusinessBean {

	@Inject
	private Validator validator;

	public void consumeCustomer(Customer customer) {
		Set<ConstraintViolation<Customer>> errors = validator.validate(customer);
		if (!errors.isEmpty()) {
			String msg = "Validation exception: ";
			for (ConstraintViolation<Customer> error : errors) {
				msg += "[" + error + "]   ";
			}
			throw new IllegalArgumentException(msg);
		}
	}
}
