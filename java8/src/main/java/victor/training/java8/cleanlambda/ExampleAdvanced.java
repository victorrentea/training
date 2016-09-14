package victor.training.java8.cleanlambda;

import static java.util.stream.Collectors.toList;

import java.util.List;

public class ExampleAdvanced {

	private static class Pair<T,U> {
		private T left;
		private U right; /* ... */													public final T getLeft() {return left;}public final U getRight() {return right;}public Pair(T left, U right) {this.left = left;this.right = right;}
	}
	
	
//	private static class Customer {
//		public boolean equalsByEmail(Customer other) {
//			this.equalsByEmail)
//		}
//	}
	
	private static class OrderItem {
		
	}
	private static class Order {
		List<OrderItem> orderItems;

		public final List<OrderItem> getOrderItems() {
			return orderItems;
		}

		public final void setOrderItems(List<OrderItem> orderItems) {
			this.orderItems = orderItems;
		}
		
	}
	
	private static class Customer {
		List<Order> orders;

		public final List<Order> getOrders() {
			return orders;
		}

		public final void setOrders(List<Order> orders) {
			this.orders = orders;
		}
		
	}
	
	
	public List<OrderItem> getAllOrderItems(Customer customer) {
		return customer.getOrders().stream()
				.flatMap(order -> order.getOrderItems().stream())
				.collect(toList());
	}
	
	
}
