package victor.training.java8.jprime.predicates;

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
		return null;
	}
	public boolean isConfidential() {
		return true;
	}
	
}
