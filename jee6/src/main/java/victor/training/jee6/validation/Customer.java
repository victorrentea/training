package victor.training.jee6.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import victor.training.jee6.validation.custom.RoMobilePhone;

public class Customer {

	@NotNull
	@Size(min = 1, max = 20)
	private String name;

	@Min(18)
	@Max(100)
	private Integer age;

	@Email
	private String email;

	@Size(min = 1)
	@Valid
	private List<Address> addresses = new ArrayList<Address>();

	@RoMobilePhone
	private String mobilePhone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

}
