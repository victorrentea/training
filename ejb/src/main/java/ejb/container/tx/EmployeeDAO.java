package ejb.container.tx;

import javax.ejb.Local;

import ejb.container.tx.model.Employee;

@Local
public interface EmployeeDAO {

	void persist(Employee employee);

	Employee getById(String id);
	
	void removeById(String employeeId);
}
