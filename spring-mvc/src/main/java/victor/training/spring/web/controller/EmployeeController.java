package victor.training.spring.web.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import victor.training.spring.model.Employee;
import victor.training.spring.model.User;
import victor.training.spring.service.HRService;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	private final static Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private HRService service;

	@ModelAttribute //("user") // default is inferred from return type
	public User getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		return new User(currentPrincipalName, authentication.getAuthorities().toString());
	}
	
	@RequestMapping
	public String showAllEmployees(Map<String, Object> model) {
		List<Employee> list = service.getAllEmployees();
		model.put("employeeList", list);
		model.put("newEmployee", new Employee());
		return "employeeList";
	}

	@RequestMapping(value = "{id}", method = GET)
	public String showEmployee(@PathVariable("id") String employeeId, Model model) {
		Employee employee = service.getEmployee(employeeId);
		model.addAttribute(employee);
		return "employeeEdit";
	}
	
	@RequestMapping(value = "create", method = POST)
	public String create(Employee employee) {
		service.createEmployee(employee);
		return "redirect:/employee/";
	}

	@RequestMapping("{id}/delete")
	public String delete(@PathVariable String id) {
		service.deleteEmployee(id);
		return "redirect:/employee/";
	}
		
	@RequestMapping(value = "{id}", method = POST)
	public String updateEmployee(@PathVariable String id, @Valid Employee employee, Errors errors) {
	    if (errors.hasErrors()) {
	        return "employeeEdit";
        }
		service.updateEmployee(id, employee);
		return "redirect:/employee/";
	}
	
	
	@RequestMapping(params = "name")
	public String findEmployeeByName(@RequestParam("name") String name,Map<String, Object> model) {
		List<Employee> employeeList = service.findEmployeesByName(name);
		model.put("employeeList", employeeList);
		model.put("newEmployee", new Employee());
		return "employeeList";
	}

	// TODO handle GET for "/employee/{id}.xml" (full URI) carrying Accept: application/xml
	// TODO mark the method to return directly the response (not a Logical View Name)
	public Employee showEmployeeAsXML(@PathVariable("id") String employeeId) {
		return service.getEmployee(employeeId);
	}
	
}
