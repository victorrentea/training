package victor.training.eepatterns;

import static org.junit.Assert.assertNotNull;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.eepatterns.locator.ServiceLocator;
import victor.training.eepatterns.soa.customer.Customer;

@RunWith(Arquillian.class)
public class LocatorTest extends AbstractArquillianTest {

	@Test
	public void getCustomerServiceThroughServiceLocator() {
		Customer service = ServiceLocator.getInstance().getCustomerService();
		assertNotNull(service);
	}

}
