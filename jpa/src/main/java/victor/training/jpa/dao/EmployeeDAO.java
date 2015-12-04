package victor.training.jpa.dao;

import victor.training.jpa.model.employee.Company;
import victor.training.jpa.model.employee.Employee;

public interface EmployeeDAO {

	void persist(Employee employee);
	
	Employee getById(Integer employeeId);

	void persistCompany(Company company);

	public abstract Employee findWithProjects(Integer employeeId);
}
