package victor.training.java8.jeeconf.predicates;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import victor.training.java8.jeeconf.predicates.OrderLine.Status;

public class OrderService {

	public void notifyCustomersOfIncompleteOrders(List<Order> orders) {
		LocalDate warningDate = LocalDate.now().plusDays(3);
		
		Set<Customer> customersToNotify = orders.stream()
				.filter(order -> order.getDeliveryDueDate().isBefore(warningDate) && 
						order.getOrderLines().stream()
						.anyMatch(orderLine -> orderLine.getStatus() != Status.IN_STOCK))
				.map(Order::getCustomer).collect(toSet());
	
		for (Customer customer : customersToNotify) {
			sendEmail(customer);
		}
	}

	private void sendEmail(Customer customer) {
		// TODO Auto-generated method stub
		
	}

}
