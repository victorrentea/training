package spring.web.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.model.Employee;
import spring.model.Task;

@Controller
@RequestMapping("rest/employee")
public class EmployeeResource {

	private static final Logger log = Logger.getLogger(EmployeeResource.class);

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Task>	createEmployee(Employee employee) throws URISyntaxException {
		log.info("Creating Employee");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(new URI("/rest/employee/1"));
		return new HttpEntity<Task>(new Task(), responseHeaders);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public HttpEntity<Employee> getEmployeeById(@PathVariable("id") String employeeId) {
		log.info("Returning Employee id = " + employeeId);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLastModified(new Date().getTime());
		return new HttpEntity<Employee>(new Employee("John Doe", "111"), responseHeaders);
	}

	@RequestMapping(value = "`", method = RequestMethod.PUT)
	public void updateEmployee(@PathVariable("id") String employeeId, Employee employee) {
		log.info("Updating Employee id = " + employeeId);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteEmployee(@PathVariable("id") String employeeId) {
		log.info("Deleting Employee id = " + employeeId);
	}

}
