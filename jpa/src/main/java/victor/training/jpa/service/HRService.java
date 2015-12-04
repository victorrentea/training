package victor.training.jpa.service;

import victor.training.jpa.model.employee.Company;
import victor.training.jpa.model.employee.Employee;

public interface HRService {

	void changeEmployeeSuccessfully(Integer employeeId);

	void changeEmployeeFailing(Integer employeeId);
	
	void createCompany(Company company);

	public abstract Employee getEmployeeWithProjects(Integer employeeId);
	
}
