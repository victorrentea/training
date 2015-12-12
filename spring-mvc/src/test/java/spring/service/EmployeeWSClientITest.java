package spring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import victor.training.spring.model.Employee;
import victor.training.spring.model.Task;
import victor.training.spring.service.EmployeeWSClient;
import victor.training.spring.service.EntityNotModifiedException;

@ContextConfiguration(locations = { "classpath:/application-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeWSClientITest {

	@Autowired
	EmployeeWSClient wsClient;
	
	String baseURI = "http://localhost:8080/spring-mvc/rest";
	
	@Test
	public void testCreateEmployee() {
		URI location = wsClient.createEmployee(mockEmployee());
		assertEquals(baseURI + "/employee/1", location.toString());
	}
	
	@Test
	public void testCreateEmployeeAsync() {
		Task task = wsClient.createEmployeeAsync(mockEmployee());
		assertNotNull(task);
	}
	
	@Test
	public void testGetEmployee() {
		Employee employee = wsClient.getEmployee("1");
		assertNotNull(employee);
		assertEquals("John Doe", employee.getName());
	}
	
	@Test
	public void testGetEmployeeOnlyIfNewer_withNewerResponse() throws ParseException {
		Employee employee = wsClient.getEmployeeOnlyIfNewer(
				"1", new SimpleDateFormat("dd/MM/yyyy").parse("1/1/2013"));
		assertEquals("John Doe", employee.getName());
	}
	
	@Test(expected = EntityNotModifiedException.class)
	public void testGetEmployeeOnlyIfNewer_withException() throws ParseException {
		wsClient.getEmployeeOnlyIfNewer(
				"1", new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2013"));
	}
	
	@Test
	public void testRemoveEmployee() {
		wsClient.removeEmployee("1");
	}
	
	@Test
	public void testUpdateEmployee() {
		wsClient.updateEmployee(mockEmployee());
	}
	
	private Employee mockEmployee() {
		Employee emp = new Employee();
		emp.setName("John Doe");
		emp.setPhone("phone nr");
		return emp;
	}
	
}
