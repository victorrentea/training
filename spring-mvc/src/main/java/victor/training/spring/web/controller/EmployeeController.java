package victor.training.spring.web.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import victor.training.spring.model.Employee;
import victor.training.spring.model.User;
import victor.training.spring.service.HRService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	private final static Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private HRService service;

	@ModelAttribute //("user") // default is inferred from return type
	public User getCurrentUsername() {
		return new User("john.doe", "John Doe");
	}
	
	@RequestMapping
	public String showAllEmployees(Map<String, Object> model) {
		List<Employee> list = service.getAllEmployees();
		model.put("employeeList", list);
		return "employeeList";
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String showEmployee(@PathVariable("id") String employeeId, Map<String, Object> model) {
		Employee employee = service.getEmployee(employeeId);
		model.put("employee", employee);
		return "employeeEdit";
	}
	
	@RequestMapping("{id}/delete")
	public String delete(@PathVariable("id") String employeeId) {
		service.deleteEmployee(employeeId);
		return "redirect:/employee/";
	}
		
	@RequestMapping(value = "{id}", method = RequestMethod.POST)	
	public String updateEmployee(@PathVariable("id") String employeeId, Employee employee) {
		service.updateEmployee(employeeId, employee);
		return "redirect:/employee/";
	}
	
	
	@RequestMapping(params = "name")
	public String findEmployeeByName(@RequestParam("name") String name,Map<String, Object> model) {
		List<Employee> employeeList = service.findEmployeesByName(name);
		model.put("employeeList", employeeList);
		return "employeeList";
	}

	// TODO handle GET for "/employee/{id}.xml" (full URI) carrying Accept: application/xml
	// TODO mark the method to return directly the response (not a Logical View Name)
	public Employee showEmployeeAsXML(@PathVariable("id") String employeeId) {
		return service.getEmployee(employeeId);
	}
	
}
