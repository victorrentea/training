package victor.training.java8.cleanlambda;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ExampleComparator {
	private static class Order {
		private Date creationDate;
		/* ... */
		public Date getCreationDate() {
			return creationDate;
		}
		public Integer getValue() {return 1;}
	}
	
	public Order getLastCreatedOrder(List<Order> orders) {
	    return orders.stream()
	    		.max(Comparator.comparing(Order::getCreationDate))
	    		.orElse(null);
	}
	
	private static class Customer {
		public String getLastName() {
			return null;
		}
		public String getFirstName() {
			return null;
		}
	}
	
	public static void main(String[] args) {
		List<Customer> customers = Arrays.asList();
		
		customers.sort(Comparator.comparing(Customer::getLastName)
		               			 .thenComparing(Comparator.comparing(Customer::getFirstName)));
		
		
		List<Order> orders = Arrays.asList();
		
		Collections.sort(orders, Comparator.comparing(Order::getValue));
		
		List<Order> sortedOrders = orders.stream()    
		    .sorted(Comparator.comparing(Order::getValue))
		    .collect(toList());
		
		System.out.println(sortedOrders);
	}
	
	
}
