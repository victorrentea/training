package victor.training.jee6;

import static victor.training.jee6.ValidationTestHelper.AddressBuilder.aValidMockAddress;
import static victor.training.jee6.ValidationTestHelper.CustomerBuilder.aValidMockCustomer;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.jee6.validation.BusinessBean;

@RunWith(Arquillian.class)
public class ValidationTest extends AbstractArquillianTest {

	@Inject
	private BusinessBean business;

	@Test
	public void tryBuiltinValidation() {
		business.consumeCustomer(aValidMockCustomer().build());
	}

	@Test(expected = IllegalArgumentException.class)
	public void age16fails() {
		business.consumeCustomer(aValidMockCustomer().withAge(16).build());
	}

	@Test(expected = IllegalArgumentException.class)
	public void emailFails() {
		business.consumeCustomer(aValidMockCustomer().withEmail("stuff@sda@gmail.com").build());
	}

	@Test(expected = IllegalArgumentException.class)
	public void mobileNumber_customValidationFails() {
		business.consumeCustomer(aValidMockCustomer().withMobilePhone("2313").build());
	}

	@Test(expected = IllegalArgumentException.class)
	public void cascadeOnAddressFails() {
		business.consumeCustomer(aValidMockCustomer().withAddress(aValidMockAddress().withStreet(null)).build());
	}

}
