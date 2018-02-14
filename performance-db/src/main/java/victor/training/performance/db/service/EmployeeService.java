package victor.training.performance.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import victor.training.performance.db.dao.EmployeeDAO;
import victor.training.performance.db.domain.Employee;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeDAO employeeDao;

	public Employee getEmployeeById(String employeeId) {
		return employeeDao.findById(employeeId);
	}

	public void createEmployee(Employee newEmployee) {
		employeeDao.persist(newEmployee);
	}
	
	public void updateEmployeePhone(String employeeId, String newPhone) {
		// WARN: needs to run in a separate Tx than the test
		Employee employee = employeeDao.getById(employeeId);
		employee.setPhone(newPhone);
		employeeDao.update(employee);
		
		if (employeeDao.countByPhone(newPhone) >= 2) {
			throw new IllegalArgumentException("Phone is already in use");
		}
	}

}
