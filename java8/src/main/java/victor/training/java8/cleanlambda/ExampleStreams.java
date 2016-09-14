package victor.training.java8.cleanlambda;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ExampleStreams {
	private static class OrderLine {

	public void activate(Object currentUser) {
		// TODO Auto-generated method stub
		
	}
	}
	private static class Order {

		public Long getExternalId() {
			return null;
		}

		public boolean hasDeliveryDateAfter(Date date) {
			// TODO Auto-generated method stub
			return false;
		}

		public Collection<OrderLine> getOrderLines() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private Object getCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public Order findOrderByExternalId(List<Order> orders, Long externalId) {
	    return orders.stream()
		    .filter(order -> order.getExternalId().equals(externalId))
		    .findFirst()
		    .get();
	}
	
	public boolean hasOverdueOrders(List<Order> orders) {
	    return orders.stream()
	          .anyMatch(order -> order.hasDeliveryDateAfter(new Date()));
	}
	
	public void activateLines(Order order) {
	    order.getOrderLines().stream()
	          .forEach(orderLine -> {orderLine.activate(getCurrentUser());});
	}
	
	
	


}
