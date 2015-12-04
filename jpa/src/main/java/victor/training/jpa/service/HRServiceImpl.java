package victor.training.jpa.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.dao.EmployeeDAO;
import victor.training.jpa.model.employee.Company;
import victor.training.jpa.model.employee.Employee;

public class HRServiceImpl implements HRService {

	@Autowired
	private EmployeeDAO dao;

	@Override
	@Transactional
	public void changeEmployeeSuccessfully(Integer employeeId) {
		Employee employee = dao.getById(employeeId);

		employee.setName("NewName");

		employee.getDetails().setStartDate(new Date());
	}

	@Override
	@Transactional
	public void changeEmployeeFailing(Integer employeeId) {
		Employee employee = dao.getById(employeeId);

		employee.setName("NewName");

		employee.getDetails().setStartDate(new Date());
		
		throw new RuntimeException();
	}
	
	@Override
	@Transactional
	public void createCompany(Company company) {
		
		dao.persistCompany(company);
	}
	
	@Override
	public Employee getEmployeeWithProjects(Integer employeeId) {
		return dao.findWithProjects(employeeId);
	}
}
