package victor.training.oo.creational.builder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CustomerValidatorTest {

	private CustomerValidator validator = new CustomerValidator();
	
	@Test
	public void validCustomer_ok() {
//		validator.validate(new CustomerBuilder()
//			.withName("John Doe")
//			.withAddress(new AddressBuilder()
//					.withCity("Bucharest")
//					.build())
//			.build());
		validator.validate(aValidCustomer().build()); // SOLUTION
	}
	
	
	@Test(expected = IllegalArgumentException.class) // SOLUTION
	public void customerWithoutName_fails() {
		validator.validate(aValidCustomer().withName(null).build()); // SOLUTION
	}
	
	@Test(expected = IllegalArgumentException.class) // SOLUTION
	public void customerWithoutAddress_fails() {
		validator.validate(aValidCustomer().withAddress((Address)null).build()); // SOLUTION
	}
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test // SOLUTION
	public void customerWithoutAddressCity_fails() {
		expectedException.expectMessage("address city");
		validator.validate(aValidCustomer().withAddress(aValidAddress().withCity("")).build()); // SOLUTION
	}

	// SOLUTION(
	private CustomerBuilder aValidCustomer() {
		return new CustomerBuilder()
				.withName("John Doe")
				.withAddress(aValidAddress());
	}

	private AddressBuilder aValidAddress() {
		return new AddressBuilder()
				.withStreetName("Viorele")
				.withCity("Bucharest");
	} 	// SOLUTION)
}