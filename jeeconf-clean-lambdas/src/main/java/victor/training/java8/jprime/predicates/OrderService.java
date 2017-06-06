package victor.training.java8.jprime.predicates;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class OrderService {

	public void notifyCustomersOfIncompleteOrders(List<Order> orders) {
		LocalDate warningDate = LocalDate.now().plusDays(3);
		
		Set<Customer> customersToNotify = orders.stream()
				.filter(OrderPredicates.hasDeliveryDateBeforeWarning(warningDate))
				.filter(this::hasOrderLinesNotInStock)
				.map(Order::getCustomer).collect(toSet());
	
		for (Customer customer : customersToNotify) {
			sendEmail(customer);
		}
	}

	public static class OrderPredicates {
		public static Predicate<Order> hasDeliveryDateBeforeWarning(LocalDate warningDate) {
			return order -> order.getDeliveryDueDate().isBefore(warningDate);
		}
	}

	private boolean hasOrderLinesNotInStock(Order order) {
		return order.getOrderLines().stream().anyMatch(OrderLine::isNotInStock);
	}

	private void sendEmail(Customer customer) {
		// TODO Auto-generated method stub
		
	}

}
