package victor.training.jee6.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Address {
	@NotNull
	@Size(min = 3, max = 80)
	private String street;

	@NotNull
	private Integer streetNumber;

	public String getStreet() {
		return street;
	}

	public void setStreet(String streetName) {
		this.street = streetName;
	}

	public Integer getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(Integer number) {
		this.streetNumber = number;
	}
}
