package victor.training.eepatterns.soa.customer.service;

import victor.training.eepatterns.soa.customer.domain.Customer;
import victor.training.eepatterns.soa.customer.domain.Order;

public class PlaceOrder {

	private final Customer customer;
	private final Order order;
	
	public PlaceOrder(Customer customer, Order order) {
		this.customer = customer;
		this.order = order;
	}
	
	public void execute() {
		System.out.println("Do a lot of stuff, call a lot of little nice private method... Clean Code!");
	}
	
}
