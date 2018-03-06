package victor.training.oo.creational.builder;

import org.apache.commons.lang.StringUtils;

public class CustomerValidator {

	public void validate(Customer customer) {
		if (StringUtils.isBlank(customer.getName())) {
			throw new IllegalArgumentException("Missing customer name");
		}
		validateAddress(customer.getAddress());
		//etc
	}
	
	private void validateAddress(Address address) {
		if (address == null) {
			throw new IllegalArgumentException("Missing customer address");
		}
		if (StringUtils.isBlank(address.getCity())) {
			throw new IllegalArgumentException("Missing address xcity");
		}
	}
}
