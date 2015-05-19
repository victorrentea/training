package victor.training.eepatterns.locator;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import victor.training.eepatterns.soa.customer.Customer;

public class ServiceLocator {

	private static ServiceLocator INSTANCE;

	private ServiceLocator() {
		init();
	}

	public static ServiceLocator getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ServiceLocator();
		}
		return INSTANCE;
	}

	private InitialContext context;

	public Customer getCustomerService() {
		return (Customer) lookup("java:/CustomerFacade");
	}

	private void init() {
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private Object lookup(String jndiName) {
		try {
			return context.lookup(jndiName);
		} catch (NamingException e) {
			// handle error uniformly
			throw new RuntimeException(e);
		}
	}
}
