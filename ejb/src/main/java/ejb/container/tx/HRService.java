package ejb.container.tx;

import javax.ejb.Local;

import ejb.container.tx.model.Employee;

@Local
public interface HRService {
	void createEmployee(Employee newEmployee);

	Employee getEmployeeById(String employeeId);

	void removeEmployee(String employeeId);

	void switchPhones(String employee1Id, String employee2Id);
}
