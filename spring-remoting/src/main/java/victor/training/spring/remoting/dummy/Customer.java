package victor.training.spring.remoting.dummy;

public class Customer {
	private Long id;
	private String name;
	private String phone;
	private String email;
	
	public Customer() {
	}
	
	public Customer(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}



	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getPhone() {
		return phone;
	}

	public final void setPhone(String phone) {
		this.phone = phone;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

}
