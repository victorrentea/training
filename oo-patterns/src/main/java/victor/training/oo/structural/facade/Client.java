package victor.training.oo.structural.facade;

import victor.training.oo.structural.facade.system.customer.Customer;
import victor.training.oo.structural.facade.system.customer.Facade;

public class Client {
	private Facade facade;

	public void registerCustomer(Customer customer) {
		facade.registerCustomer(customer);
	}

	public static void main(String[] args) {
		Client client = new Client();
		// setup client
		// client NOW depends just on the facade. The facade interacts with the
		// internal components.
		// it HIDES the implementation details of the system.
		// LOWERS CLIENT COUPLING.

		client.facade = new Facade();

		client.registerCustomer(new Customer("John Doe", "john.doe@pentagon.us"));
	}
}
