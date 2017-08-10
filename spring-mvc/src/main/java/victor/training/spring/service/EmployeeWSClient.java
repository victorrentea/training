package victor.training.spring.service;

import java.net.URI;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import victor.training.spring.model.Employee;
import victor.training.spring.model.Task;

public class EmployeeWSClient {

	// TODO 2: inject directly here (nothing in Spring xml)
	private String base;

	public Employee getEmployee(String employeeId) {
		return new RestTemplate().getForObject(
				base + "/employee/{id}", Employee.class, employeeId);
	}
	
	public Employee getEmployeeOnlyIfNewer(String employeeId, Date date) {
		ResponseEntity<Employee> response = new RestTemplate().getForEntity(
				base + "/employee/{id}", Employee.class, employeeId);
		
		if (!date.before(new Date(response.getHeaders().getLastModified()))) {
			throw new IllegalStateException("Entity did not change");
		} else {
			return response.getBody();
		}
	}
	
	public Task createEmployeeAsync(Employee employee) {
		return new RestTemplate().postForObject(base + "/employee", employee, Task.class);
	}
	
	public URI createEmployee(Employee employee) {
		return new RestTemplate().postForLocation(base + "/employee", employee);
	}

	public void removeEmployee(String employeeId) {
		new RestTemplate().delete("/employee/{id}", employeeId);
	}
	
	public void updateEmployee(Employee employee) {
		new RestTemplate().put(base + "/employee/{id}", employee, employee.getId()); 
	}

	public void setBase(String base) {
		this.base = base;
	}
	
}
