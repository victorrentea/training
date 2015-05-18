package victor.training.oo.structural.facade.system.customer;

public class CustomerRepository {
	public Customer getCustomerByEmail(String email) {
		System.out.println("Lookup existing customer by email (natural key)");
		return null; // let's say it doesn't find it
	}
}
