package victor.training.oo.creational.builder;

public class Address {
	private String streetName;
	private String streetNumber;
	private String city;
	private String country;

	public static class Builder {
		private final Address address = new Address();

		public Builder withStreetName(String streetName) {
			address.setStreetName(streetName);
			return this;
		}

		public Address build() {
			return address;
		}
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
