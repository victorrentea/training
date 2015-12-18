package batch.model.domain;

import org.apache.commons.lang.StringUtils;

public class Address {

	public static final String DEFAULT_COUNTRY_CODE = "BE";

	private String street;

	private String number;

	private String box;

	private String postalCode;

	private String city;

	private String country = DEFAULT_COUNTRY_CODE;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getBox() {
		return box;
	}

	public void setBox(String box) {
		this.box = box;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	@Override
	public String toString() {
		String result = "";
		result = getStreet() + " " + getNumber();
		if (StringUtils.isNotBlank(getBox())) {
			result += " / " + getBox();
		}
		result += ", " + getPostalCode() + " " + getCity();
		return result;
	}
}
