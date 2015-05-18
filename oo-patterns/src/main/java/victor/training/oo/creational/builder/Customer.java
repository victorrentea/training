package victor.training.oo.creational.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {

	private String name;
	private String phone;
	private List<String> labels = new ArrayList<>();
	private Address address;
	private Date createDate;

	public static class Builder {
		private final Customer customer = new Customer();

		public Builder withName(String name) {
			customer.setName(name);
			return this;
		}

		public Builder withLabel(String label) {
			customer.getLabels().add(label);
			return this;
		}

		public Builder withAddress(Address address) {
			customer.setAddress(address);
			return this;
		}

		public Customer build() {
			return customer;
		}
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
