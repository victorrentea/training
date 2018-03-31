package victor.clean.lambdas;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import victor.clean.lambdas.CherishYourPredicates.OrderLine.Status;

public class CherishYourPredicates {

	public void notifyCustomersOfIncompleteOrders(List<Order> orders) {
		LocalDate warningDate = LocalDate.now().plusDays(3);
		
		var customersToNotify = orders.stream()
				.filter(order -> order.getDeliveryDueDate().isBefore(warningDate) && 
						order.getOrderLines().stream().anyMatch(ol -> ol.getStatus() != Status.IN_STOCK))
				.map(o -> {return o.getCustomer();}).collect(toSet());
		
	
		for (Customer customer : customersToNotify) {
			sendEmail(customer);
		}
	}

	private void sendEmail(Customer customer) { /* stuff */ }

	
	public static class Order {
		private Customer customer;
		private LocalDate deliveryDueDate;
		private List<OrderLine> orderLines = new ArrayList<>();
		private boolean confidential;
		public Customer getCustomer() {
			return customer;
		}
		public void setCustomer(Customer customer) {
			this.customer = customer;
		}
		public LocalDate getDeliveryDueDate() {
			return deliveryDueDate;
		}
		public void setDeliveryDueDate(LocalDate deliveryDueDate) {
			this.deliveryDueDate = deliveryDueDate;
		}
		public List<OrderLine> getOrderLines() {
			return orderLines;
		}
		public void setOrderLines(List<OrderLine> orderLines) {
			this.orderLines = orderLines;
		}
		public boolean isConfidential() {
			return confidential;
		}
		public void setConfidential(boolean confidential) {
			this.confidential = confidential;
		}
		
	}
	
	public static class OrderLine {
		public enum Status {
			IN_STOCK, AT_PROVIDER, UNAVAILABLE
		}
		private Status status;
		public Status getStatus() {
			return status;
		}
		public void setStatus(Status status) {
			this.status = status;
		}
		
	}

	public static class Customer {
	}
	
}

