package victor.training.oo.creational.builder;

import java.util.ArrayList;
import java.util.List;

public class BuilderPlay {

	public static void main(String[] args) {
		
//		Customer customer = new Customer(); // INITIAL
//		customer.setName("John Doe"); // INITIAL
//		List<String> labels = new ArrayList<String>(); // INITIAL
//		labels.add("Label1"); // INITIAL
//		customer.setLabels(labels); // INITIAL
//		Address address = new Address(); // INITIAL
//		address.setStreetName("Viorele"); // INITIAL
//		customer.setAddress(address); // INITIAL
		
		
		// SOLUTION(
		Customer customer = new Customer.Builder()
			.withName("John Doe")
			.withLabel("Label1")
			.withAddress(new Address.Builder()
				.withStreetName("Viorele")
				.build())
			.build();
		// SOLUTION)
		System.out.println("Customer name: " + customer.getName());
		System.out.println("Customer address: " + customer.getAddress().getStreetName());
		
		// 2
		String s = new StringBuilder()
			.toString();
	}
}
