package victor.training.java8.cleanlambda;

import static java.util.stream.Collectors.toList;

import java.util.List;

public class Example1 {

	private static class Customer {
		public enum Type {
			A,B
		}
		public Type getType() {
			return Type.A;
		}
		public Long getId() {
			return 1L;
		}
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	private static class CustomerDto {

		public CustomerDto(Long id, String name) {
			// TODO Auto-generated constructor stub
		}
		
	}
	
	public static void main(String[] args) {
		Customer customer = new Customer();
		
		new CustomerDto(customer.getId(), customer.getName());
		
	}
	
	public List<Long> getIdsOfCustomerOfType(List<Customer> allCustomers, Customer.Type type) {
		return allCustomers.stream()
			.filter(customer -> customer.getType() == type)
			.map(customer -> customer.getId())
			.collect(toList());
	}
}
