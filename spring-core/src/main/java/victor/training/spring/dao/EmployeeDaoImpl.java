package victor.training.spring.dao;

import org.springframework.stereotype.Repository;

import victor.training.spring.model.Employee;

@Repository // SOLUTION
public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {

	@Override
	public void persist(Employee employee) {
		System.out.println("All OK!");
	}

	@Override
	public Employee getById(String id) {
		return new Employee("John Doe", "9888");
	}

	@Override
	public void update(Employee employee) {
		System.out.println("Employee updated");
	}

	@Override
	public void removeById(String employeeId) {
		System.out.println("Employee removed");
	}

}
