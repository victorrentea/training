package victor.training.java8.patterns;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StrategyExternallyDecided {

	private static class Customer {
		private final String name;

		public Customer(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
	}
	public static void main(String[] args) {
		List<Customer> list = Arrays.asList(new Customer("Bravo"), new Customer("Alpha"), new Customer("Romeo"));
		
		list.sort(Comparator.comparing(Customer::getName));
	}
}
