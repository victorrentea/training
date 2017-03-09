package victor.training.java8.voxxed.order;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import victor.training.java8.voxxed.order.entity.Customer;
import victor.training.java8.voxxed.order.entity.Order;
import victor.training.java8.voxxed.order.entity.OrderLine;

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
		return customer.getOrders().stream()
				.filter(Order::isActive)
				.collect(toList()); 
	}
	
	/**
	 * @return the Order in the list with the given id  
	 * - ...Any or ...First ?
	 * - what do you do when you don't find it ? null/throw/Optional ?
	 */
	public Order p2_getOrderById(List<Order> orders, long orderId) {
		return orders.stream()
				.filter(order -> order.getId().equals(orderId))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Nice message"));
	}
	
	/**
	 * @return true if customer has at least one order with status ACTIVE
	 */
	public boolean p3_hasActiveOrders(Customer customer) {
		return customer.getOrders().stream()
				.anyMatch(Order::isActive); 
	}

	/**
	 * An Order can be returned if it doesn't contain 
	 * any OrderLine with isSpecialOffer()==true
	 */
	public boolean p4_canBeReturned(Order order) {
		return order.getOrderLines().stream()
				.noneMatch(OrderLine::isSpecialOffer);
	}
	
	// ---------- select the best ------------
	
	/**
	 * The order with maximum price. 
	 * i.e. the most expensive Order, or null if no Orders
	 * - Challenge: return an Optional<creationDate>
	 */
	public Optional<LocalDate> p5_getMaxPriceOrder(Customer customer) {
		Optional<Order> orderOpt = customer.getOrders().stream()
				.max(Comparator.comparing(Order::getTotalPrice));
		return orderOpt
				.map(Order::getCreationDate); 
	}
	
	/**
	 * last 3 order sorted descending by creationDate
	 */
	public List<Order> p6_getLast3Orders(Customer customer) {
		Comparator<Order> creationDateDesc = comparing(Order::getCreationDate).reversed();
		return customer.getOrders().stream()
				.sorted(creationDateDesc.thenComparing(Order::getTotalPrice))
				.limit(3)
				.collect(toList()); 
	}
	
	
}
