package spring.service;

import spring.model.Employee;

public interface EmployeeService {

	void createEmployee(Employee newEmployee);

	Employee getEmployeeById(String employeeId);

	void removeEmployee(String employeeId);

	void switchPhones(String employee1Id, String employee2Id);

	void m1();
	
	void m2();

}
