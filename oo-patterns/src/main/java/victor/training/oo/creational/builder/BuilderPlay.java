package victor.training.oo.creational.builder;

//import java.util.ArrayList; // INITIAL
//import java.util.List; // INITIAL

public class BuilderPlay {

	public static void main(String[] args) {
		
		// INITIAL(
//		Customer customer = new Customer(); 
//		customer.setName("John Doe"); 
//		List<String> labels = new ArrayList<String>(); 
//		labels.add("Label1"); 
//		customer.setLabels(labels); 
//		Address address = new Address(); 
//		address.setStreetName("Viorele"); 
//		address.setStreetNumber(4); 
//		address.setCity("Bucharest");
//		customer.setAddress(address); 
		// INITIAL)
		
		
		// SOLUTION(
		Customer customer = new CustomerBuilder()
			.withName("John Doe")
			.withLabel("Label1")
			.withAddress(new AddressBuilder()
				.withStreetName("Viorele")
				.withStreetNumber(4)
				.withCity("Bucharest"))
			.build();
		// SOLUTION)
		
		System.out.println("Customer name: " + customer.getName());
		System.out.println("Customer address: " + customer.getAddress().getStreetName());
		
		// 2
		String s = new StringBuilder()
			.toString();
	}
}
