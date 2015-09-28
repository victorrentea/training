package victor.training.spring.basic.dao;

import victor.training.spring.basic.model.Employee;

public interface EmployeeDao {

	void persist(Employee employee);

	Employee getById(String id);
	
	void update(Employee employee);
	
	void removeById(String employeeId);
}
