package victor.clean.lambdas;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import victor.clean.lambdas.CherishYourPredicates.OrderLine.Status;

public class CherishYourPredicates {

	public void notifyCustomersOfIncompleteOrders(List<Order> orders) {
		LocalDate warningDate = LocalDate.now().plusDays(3);
		
		Set<Customer> customersToNotify = orders.stream()
				.filter(order -> order.getDeliveryDueDate().isBefore(warningDate) && 
						order.getOrderLines().stream().anyMatch(ol -> ol.getStatus() != Status.IN_STOCK))
				.map(o -> {return o.getCustomer();}).collect(toSet());
	
		for (Customer customer : customersToNotify) {
			sendEmail(customer);
		}
	}

	private void sendEmail(Customer customer) { /* stuff */ }

	
	public static class Order {
		@Getter @Setter private Customer customer;
		@Getter @Setter private LocalDate deliveryDueDate;
		@Getter private List<OrderLine> orderLines = new ArrayList<>();
		@Getter private boolean confidential;
	}
	
	public static class OrderLine {
		public enum Status {
			IN_STOCK, AT_PROVIDER, UNAVAILABLE
		}
		@Getter @Setter private Status status;
	}

	public static class Customer {
	}
	
}

