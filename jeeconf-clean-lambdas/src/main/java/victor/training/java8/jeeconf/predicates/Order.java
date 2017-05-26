package victor.training.java8.jeeconf.predicates;

import java.time.LocalDate;
import java.util.List;

public class Order {

	private Customer customer;

	public final Customer getCustomer() {
		return customer;
	}

	public final void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDate getDeliveryDueDate() {
		return null;
	}

	public List<OrderLine> getOrderLines() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
