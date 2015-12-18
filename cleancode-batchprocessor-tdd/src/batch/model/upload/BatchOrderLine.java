package batch.model.upload;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.Digits;
import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.Pattern;

import batch.model.domain.Box;
import batch.model.domain.BoxType;
import batch.model.domain.GoodCategory;



/** equivalent to a Box */
public class BatchOrderLine {

	private final Integer rowNum;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String phoneNumber;
	
	@NotEmpty
	private String street;
	
	@NotEmpty
	private String streetNr;
	
	@Digits(integerDigits=3, fractionalDigits=0)
	@Length(min=1)
	private String floor;
	
	@NotEmpty
	@Pattern(regex = "[0-9]{4}")
	private String postalCode;
	
	@NotEmpty
	private String town;
	
	@NotEmpty
	@Email
	private String email;
	
	@Digits(integerDigits=10, fractionalDigits=2)
	private String price;
	
	@EnumLiteral(BoxType.class)
	private String boxType;
	
	@EnumLiteral(GoodCategory.class)
	private String goodsType;
	
	private String orderReference;
	
	private String description;

	// fields populated during batch upload processing :
	private Box box;
	private List<String> errors = new ArrayList<String>();

	public BatchOrderLine(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public void addError(String error) {
		box = null;
		errors.add(error);
	}

	public List<String> getErrors() {
		return errors;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNr() {
		return streetNr;
	}

	public void setStreetNr(String streetNr) {
		this.streetNr = streetNr;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getOrderReference() {
		return orderReference;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	public String[] getConsumerFields() {
		return new String[]{
				getFirstName(), getLastName(), getPhoneNumber(), getStreet(), getStreetNr(), 
				getFloor(), getPostalCode(), getTown(), getEmail()};
	}

	public static class Builder {
		private final BatchOrderLine line;

		public Builder(BatchOrderLine line) {
			this.line = line;
		}

		public static Builder newLine(int rowNum) {
			return new Builder(new BatchOrderLine(rowNum));
		}

		public BatchOrderLine build() {
			return line;
		}

		public Builder withFirstName(String value) {
			line.setFirstName(value);
			return this;
		}

		public Builder withLastName(String value) {
			line.setLastName(value);
			return this;
		}

		public Builder withPhoneNumber(String value) {
			line.setPhoneNumber(value);
			return this;
		}

		public Builder withStreet(String value) {
			line.setStreet(value);
			return this;
		}

		public Builder withStreetNr(String value) {
			line.setStreetNr(value);
			return this;
		}

		public Builder withFloor(String value) {
			line.setFloor(value);
			return this;
		}

		public Builder withPostalCode(String value) {
			line.setPostalCode(value);
			return this;
		}

		public Builder withTown(String value) {
			line.setTown(value);
			return this;
		}

		public Builder withEmail(String value) {
			line.setEmail(value);
			return this;
		}

		public Builder withPrice(String value) {
			line.setPrice(value);
			return this;
		}

		public Builder withBoxType(String value) {
			line.setBoxType(value);
			return this;
		}

		public Builder withGoodsType(String value) {
			line.setGoodsType(value);
			return this;
		}

		public Builder withOrderReference(String value) {
			line.setOrderReference(value);
			return this;
		}

		public Builder withOrderDescription(String value) {
			line.setDescription(value);
			return this;
		}
	}

	public boolean isValid() {
		return !hasErrors();
	}
}
