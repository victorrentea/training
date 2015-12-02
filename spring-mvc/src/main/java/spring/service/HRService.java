package spring.service;

import java.util.List;

import spring.model.Employee;

public interface HRService {

	List<Employee> getAllEmployees();

	Employee getEmployee(String employeeId);

	void updateEmployee(String employeeId, Employee employee);

	void deleteEmployee(String employeeId);

	List<Employee> findEmployeesByName(String name);

}
