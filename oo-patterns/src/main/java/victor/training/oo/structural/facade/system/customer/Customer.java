package victor.training.oo.structural.facade.system.customer;

public class Customer {
	private String name;
	private String email;

	public Customer(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

}
