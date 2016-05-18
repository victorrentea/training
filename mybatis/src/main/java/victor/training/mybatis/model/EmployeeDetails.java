package victor.training.mybatis.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class EmployeeDetails {
	private Integer id;
	private Date startDate;
	private Employee employee;
	
	public EmployeeDetails() {
	}

	public EmployeeDetails(Integer id, Date startDate) {
		this.id = id;
		this.startDate = startDate;
	}

	public EmployeeDetails(Integer id, Date startDate, Employee employee) {
		this.id = id;
		this.startDate = startDate;
		this.employee = employee;
	}

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
