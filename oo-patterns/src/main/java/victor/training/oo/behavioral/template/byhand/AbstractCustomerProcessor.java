package victor.training.oo.behavioral.template.byhand;

public abstract class AbstractCustomerProcessor {
	public void registerCustomer() {
		System.out.println("Start register the customer");
		
		// get the customer from the database
		Customer customer = new Customer();
		
		if (customerIsValid(customer)) {
			notifyCustomerOfFailure(customer);
			return;
		} 

		// else call several external web services
		
		// write some more data in the database 
		
		// do even more stuff
		
		notifyCustomerOfSuccess(customer);
	}

	protected abstract void notifyCustomerOfFailure(Customer customer);

	protected void notifyCustomerOfSuccess(Customer customer) {
		// empty body method - hook method
		// if the subclass wants, it can override it (it's protected!)
	}

	private boolean customerIsValid(Customer customer) {
		// do some validations
		return true;
	}
	
	
}
