package victor.training.spring.dao;

import victor.training.spring.model.Employee;

public interface EmployeeDao {

	void persist(Employee employee);

	Employee getById(String id);
	
	void update(Employee employee);
	
	void removeById(String employeeId);
}
