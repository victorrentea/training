package victor.training.spring.basic.service;

import victor.training.spring.basic.model.Employee;

public interface HRService {

	void createEmployee(Employee newEmployee);

	Employee getEmployeeById(String employeeId);

	void removeEmployee(String employeeId);

	void switchPhones(String employee1Id, String employee2Id);

	String getMyProperty();
	
}
