package victor.training.spring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import victor.training.spring.model.Employee;

public class HRServiceMockImpl implements HRService {
	private final static Logger log = LoggerFactory.getLogger(HRServiceMockImpl.class);

	private Map<String, Employee> employees = new HashMap<String, Employee>();

	public HRServiceMockImpl() {
		createEmployee(new Employee("John Doe", "0213151232"));
		createEmployee(new Employee("Mike Doe", "01281548223"));
		createEmployee(new Employee("Johan Doe", "0122674323"));
		createEmployee(new Employee("Uncle Sam", "0123151323"));
	}
	
	public void createEmployee(Employee employee) {
		employees.put(employee.getId(), employee);
	}
	
	public List<Employee> getAllEmployees() {
		log.debug("Returning all employees");
		return new ArrayList<Employee>(employees.values());
	}

	public Employee getEmployee(String employeeId) {
		log.debug("Returning employee id: " + employeeId);
		return employees.get(employeeId);
	}

	public void updateEmployee(String employeeId, Employee employee) {
		log.debug("Updating employee id: " + employeeId);
		employees.put(employeeId, employee);
	}
	
	// TODO Security: limit access
//	@RolesAllowed("ROLE_ADMIN")
	public void deleteEmployee(String employeeId) {
		log.debug("Deleting employee id: " + employeeId);
		employees.remove(employeeId);		
	}
	
	public List<Employee> findEmployeesByName(String name) {
		log.debug("Finding employee with name: " + name);
		List<Employee> list = new ArrayList<Employee>();
		for (Employee employee : employees.values()) {
			if (employee.getName().contains(name)) {
				list.add(employee);
			}
		}
		return list;
	}

}
