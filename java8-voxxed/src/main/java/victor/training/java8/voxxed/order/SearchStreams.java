package victor.training.java8.voxxed.order;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;

import victor.training.java8.voxxed.order.entity.Customer;
import victor.training.java8.voxxed.order.entity.Order;

public class SearchStreams {
	
	/**
	 * FIRST OF ALL: Add the following "Favourite" static imports:
	 * Eclipse: Window > Preferences > type "Favo" > Favorites >
	 * 					> New Type > Browse > and type the class name for:
		+ java.time.LocalDate
		+ java.util.stream.Collectors
	 */
	
	/**
	 * #1
	 * - shorten/clean it up
	 */
	public List<Order> getActiveOrders(Customer customer) {	
		//return customer.getOrders(); // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
				.filter(order -> order.isActive())
				.collect(toList());
		// SOLUTION)
	}
	
	/**
	 * #2
	 * @return the Order in the list with the given id  
	 * - what do you do when you don't find it ? null/throw/Optional ?
	 * - ...Any or ...First ?
	 */
	public Order getOrderById(List<Order> orders, long orderId) {
		//return null; // orders.stream()// INITIAL
		// SOLUTION(
		return orders.stream()
				.filter(order -> order.getId().equals(orderId))
				.findAny() // vs. findFirst() 
				.get(); // alternatives ?
		// SOLUTION)
	}
	
	/**
	 * #3
	 * @return true if customer has at least one ACTIVE order
	 */
	public boolean hasActiveOrders(Customer customer) {
		//return true; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream() 
				//.filter(order -> order.getCreationDate().getYear() == LocalDate.now().getYear())// Change: daca v-as fi spus ca intre orderurile din anul curent ?
				.anyMatch(order -> order.isActive());
		// SOLUTION)
	}

	/**
	 * #4
	 * An Order can be returned if it doesn't contain 
	 * any special offer order lines (OrderLine.isSpecialOffer)
	 */
	public boolean canBeReturned(Order order) {
		//return true; // Order.getOrderLines() // INITIAL
		// SOLUTION(
		return order.getOrderLines().stream()
					//.filter(OrderLine::wasDelivered) // Change: daca v-as fi spus ca doar orderline-urile livrate...
					.noneMatch(line -> line.isSpecialOffer());
		// SOLUTION)
	}
	
	// ---------- select the best ------------
	
	/**
	 * #5
	 * The order with the maximum price. 
	 * i.e. the most expensive Order, or null if no Orders
	 * - Challenge: return an Optional<creationDate>
	 */
	public Order getMaxPriceOrder(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
					.max(Comparator.comparing(Order::getTotalPrice))
					.orElse(null); // better to return Optional ?
		// SOLUTION)
	}
	
	/**
	 * #6
	 * by the creationDate.
	 */
	public List<Order> getLast3Orders(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
					.sorted(Comparator.comparing(Order::getCreationDate).reversed())
					.limit(3)
					.collect(toList());
		// SOLUTION)
	}
	
	
}
