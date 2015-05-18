package victor.training.oo.structural.facade.system.customer;

public class Facade {
	// somehow obtain the references to the internal components
	// (e.g. request injection with @EJB or @Inject)
	private final CustomerRepository repository = new CustomerRepository();
	private final CustomerRegistration registration = new CustomerRegistration();
	private final NotificationService notification = new NotificationService();

	
	public void registerCustomer(Customer customer) {
		if (repository.getCustomerByEmail(customer.getEmail()) == null) {
			registration.registerCustomer(customer);
			notification.sendActivationLink(customer);
		} else {
			throw new IllegalArgumentException("Customer already registered with email '" + customer.getEmail() + "'");
		}
	}
}
