package victor.training.java8.stream.order.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Customer {

	private List<Order> orders = new ArrayList<>();

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Customer(Order... orders) {
		this.orders = Arrays.asList(orders);
	}
	
}
