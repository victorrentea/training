package victor.training.jpa.service;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.entity.employee.Employee;
import victor.training.jpa.entity.employee.Project;
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
	
	public void createEmployee(Employee employee) {
		if (repo.countByName(employee.getName()) >= 1) {
			throw new IllegalArgumentException("Another employee with the same name aleady exists");
		}
		repo.persist(employee);
	}
	
	public void generateExport(Writer writer) throws IOException {
		for (Employee e : repo.getAllFetchProjects()) {
			List<String> projectNames = e.getProjects().stream().map(Project::getName).collect(toList());
			writer.write(e.getName() + " works on the projects: " + projectNames);
		}
	}
}
