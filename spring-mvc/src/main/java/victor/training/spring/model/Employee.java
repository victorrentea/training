package victor.training.spring.model;


import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee {
	private String id = UUID.randomUUID().toString();
	@NotNull
	@Size(min = 2, max = 50)
	private String name;
	@NotNull
	@Size(min = 8, max = 14)
	private String phone;

	public Employee() {
	}

	public Employee(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String firstName) {
		this.name = firstName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phoneNumber) {
		this.phone = phoneNumber;
	}
}
