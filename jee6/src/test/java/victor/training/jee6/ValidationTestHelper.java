package victor.training.jee6;

import victor.training.jee6.validation.Address;
import victor.training.jee6.validation.Customer;

public class ValidationTestHelper {

	static class AddressBuilder {
		private final Address address = new Address();

		private AddressBuilder() {

		}

		public static AddressBuilder aValidMockAddress() {
			return new AddressBuilder().withStreet("A Street Name").withStreetNumber(12);
		}

		public AddressBuilder withStreet(String street) {
			address.setStreet(street);
			return this;
		}

		public AddressBuilder withStreetNumber(Integer number) {
			address.setStreetNumber(number);
			return this;
		}

		public Address build() {
			return address;
		}
	}

	static class CustomerBuilder {
		private final Customer customer = new Customer();

		private CustomerBuilder() {

		}

		public static CustomerBuilder aValidMockCustomer() {
			return new CustomerBuilder().withAge(20).withEmail("thecustomer@gmail.com").withMobilePhone("0720019566")
					.withName("TheCustomer Name").withAddress(AddressBuilder.aValidMockAddress());
		}

		public CustomerBuilder withAge(Integer age) {
			customer.setAge(age);
			return this;
		}

		public CustomerBuilder withEmail(String email) {
			customer.setEmail(email);
			return this;
		}

		public CustomerBuilder withMobilePhone(String mobilePhone) {
			customer.setMobilePhone(mobilePhone);
			return this;
		}

		public CustomerBuilder withName(String name) {
			customer.setName(name);
			return this;
		}

		public CustomerBuilder withAddress(Address address) {
			customer.getAddresses().add(address);
			return this;
		}

		public CustomerBuilder withAddress(AddressBuilder address) {
			customer.getAddresses().add(address.build());
			return this;
		}

		public Customer build() {
			return customer;
		}
	}
}
