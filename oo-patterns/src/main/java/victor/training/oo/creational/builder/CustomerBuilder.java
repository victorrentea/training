package victor.training.oo.creational.builder;

public class CustomerBuilder {
	private final Customer customer = new Customer();

	public CustomerBuilder withName(String name) {
		customer.setName(name);
		return this;
	}

	// SOLUTION(
	public CustomerBuilder withLabel(String label) {
		customer.getLabels().add(label);
		return this;
	}

	public CustomerBuilder withAddress(Address address) { 
		customer.setAddress(address);
		return this;
	}
	
	public CustomerBuilder withAddress(AddressBuilder addressBuilder) { 
		return withAddress(addressBuilder.build());
	}
	// SOLUTION)

	public Customer build() {
		return customer;
	}
}