package victor.training.jpa.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.entity.employee.Employee;
import victor.training.jpa.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repo;

	@Transactional
	public void changeEmployeeSuccessfully(Integer employeeId) {
		Employee employee = repo.getById(employeeId);

		employee.setName("NewName");

		employee.getDetails().setStartDate(new Date());
	}

	@Transactional
	public void changeEmployeeFailing(Integer employeeId) {
		Employee employee = repo.getById(employeeId);

		employee.setName("NewName");

		employee.getDetails().setStartDate(new Date());
		
		throw new RuntimeException();
	}
	
	public Employee getEmployeeWithProjects(Integer employeeId) {
		return repo.findWithProjects(employeeId);
	}
}
