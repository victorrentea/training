package spring.dao;

import spring.model.Employee;

public interface EmployeeDAO {

	void persist(Employee employee);

	Employee getById(String id);
	
	void update(Employee employee);
	
	void removeById(String employeeId);
}
