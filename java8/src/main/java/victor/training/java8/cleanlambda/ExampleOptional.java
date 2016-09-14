package victor.training.java8.cleanlambda;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ExampleOptional {
	
	private static class Address{
		String getStreet() {
			return null;
		}
	}
	private static class Customer {
		Address getAddress() {return null;}
	}
	
	public static void main(String[] args) {
		Customer customer = new Customer(); 
		
		String street = Optional.of(customer)
			.map(Customer::getAddress)
			.map(Address::getStreet)
			.orElse(null);
		
		System.out.println(street);
	}
	
	{Customer customer = new Customer();
	
	
		String street = null;
		if (customer.getAddress() != null) {
			street = customer.getAddress().getStreet();
		}
		
		
		
		
		System.out.println(street);
	}
	
	
	public Supplier<List<Customer>> selectCustomersMatchingPredicate(
			Supplier<List<Customer>> customers, Predicate<Customer> predicate) {
		
        return () -> customers.get().stream()
			            .filter(predicate)
			            .collect(toList());
	}
		
}
