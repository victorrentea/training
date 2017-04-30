package victor.training.jpa.facade.dto;

import java.util.Date;

import victor.training.jpa.entity.Employee;

public class EmployeeDto {

	public String name;
	public Integer siteId;
	public Date startDate;
	
	public EmployeeDto() {
	}
	
	public EmployeeDto(Employee employee) {
		name = employee.getName();
		siteId = employee.getSite()!=null?employee.getSite().getId():null;
		startDate = employee.getDetails()!=null?employee.getDetails().getStartDate():null;
	}
	
	

}
