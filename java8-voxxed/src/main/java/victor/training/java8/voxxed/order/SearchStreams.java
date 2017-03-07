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
	 * - shorten/clean it up
	 */
	public List<Order> p1_getActiveOrders(Customer customer) {	
		//return customer.getOrders().stream().collect(toList()); // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
				.filter(order -> order.isActive())
				.collect(toList());
		// SOLUTION)
	}
	
	/**
	 * @return the Order in the list with the given id  
	 * - ...Any or ...First ?
	 * - what do you do when you don't find it ? null/throw/Optional ?
	 */
	public Order p2_getOrderById(List<Order> orders, long orderId) {
		//return null; // orders.stream()// INITIAL
		// SOLUTION(
		return orders.stream()
				.filter(order -> order.getId().equals(orderId))
				.findAny() // vs. findFirst() 
				.get(); // alternatives ?
		// SOLUTION)
	}
	
	/**
	 * @return true if customer has at least one order with status ACTIVE
	 */
	public boolean p3_hasActiveOrders(Customer customer) {
		//return true; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream() 
				//.filter(order -> order.getCreationDate().getYear() == LocalDate.now().getYear())// Change: daca v-as fi spus ca intre orderurile din anul curent ?
				.anyMatch(order -> order.isActive());
		// SOLUTION)
	}

	/**
	 * An Order can be returned if it doesn't contain 
	 * any OrderLine with isSpecialOffer()==true
	 */
	public boolean p4_canBeReturned(Order order) {
		//return true; // order.getOrderLines().stream() // INITIAL
		// SOLUTION(
		return order.getOrderLines().stream()
					//.filter(OrderLine::wasDelivered) // Change: daca v-as fi spus ca doar orderline-urile livrate...
					.noneMatch(line -> line.isSpecialOffer());
		// SOLUTION)
	}
	
	// ---------- select the best ------------
	
	/**
	 * The order with maximum price. 
	 * i.e. the most expensive Order, or null if no Orders
	 * - Challenge: return an Optional<creationDate>
	 */
	public Order p5_getMaxPriceOrder(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
					.max(Comparator.comparing(Order::getTotalPrice))
					.orElse(null); // better to return Optional ?
		// SOLUTION)
	}
	
	/**
	 * sorted descending by creationDate
	 */
	public List<Order> p6_getLast3Orders(Customer customer) {
		//return null; // INITIAL
		// SOLUTION(
		return customer.getOrders().stream()
					.sorted(Comparator.comparing(Order::getCreationDate).reversed())
					.limit(3)
					.collect(toList());
		// SOLUTION)
	}
	
	
}
