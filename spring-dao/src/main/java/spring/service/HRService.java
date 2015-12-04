package spring.service;

import spring.model.Employee;

public interface HRService {

	void createEmployee(Employee newEmployee);

	Employee getEmployeeById(String employeeId);

	void removeEmployee(String employeeId);

	void switchPhones(String employee1Id, String employee2Id);

}
