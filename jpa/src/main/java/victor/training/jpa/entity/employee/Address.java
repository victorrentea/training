package victor.training.jpa.entity.employee;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	@Column(name="ADDRESS_STREET")
	private String street;
	@Column(name="ADDRESS_POSTAL_CODE")
	private String postalCode;
	@Column(name="ADDRESS_CITY")
	private String city;
	@Column(name="ADDRESS_COUNTRY")
	private String countryIso;
	
	
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
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
	public String getCountryIso() {
		return countryIso;
	}
	public void setCountryIso(String countryIso) {
		this.countryIso = countryIso;
	}
	
	
	
}
