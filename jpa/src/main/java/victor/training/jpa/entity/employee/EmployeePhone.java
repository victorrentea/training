package victor.training.jpa.entity.employee;

import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE_PHONE")
@SequenceGenerator(name="seq", sequenceName = "EMP_PHONE_SEQ") // SOLUTION
public class EmployeePhone  {

	public enum Type {
		OFFICE, PERSONAL, HOME
	}

	@Id
	@GeneratedValue(generator = "seq") // SOLUTION
	//@GeneratedValue // TODO look in logs: what does this do ? // INITIAL
	private Integer id;
	
	private String phoneNumber;
	
//	@Enumerated(EnumType.STRING) // INITIAL
//	private Type type; // INITIAL
	
	private String type;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private Employee employee;

	public EmployeePhone() {
	}
	
	
	
	public EmployeePhone(String phoneNumber, Type type) {
		this.phoneNumber = phoneNumber;
//		this.type = type; // INITIAL
		setType(type); // SOLUTION
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Type getType() {
//		return type; // INITIAL
		return Stream.of(Type.values()).filter(e -> e.name().startsWith(type)).findAny().get(); // SOLUTION
	}

	public void setType(Type type) {
//		this.type = type; // INITIAL
		this.type = type.name().substring(0, 1); // SOLUTION
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	
}
