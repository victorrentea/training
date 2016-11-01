package victor.training.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.entity.employee.Employee;
import victor.training.jpa.entity.employee.EmployeeDetails;
import victor.training.jpa.entity.employee.Project;
import victor.training.jpa.repository.EmployeeRepository;
import victor.training.jpa.service.EmployeeService;
import victor.training.jpa.test.util.TransactionUtil;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
/** !! Look closely !! */
public class SpringTxPlay {
	@PersistenceContext
	protected EntityManager em;
	@Autowired
	protected EmployeeRepository employeeRepo;
	@Autowired
	protected EmployeeService service;
	@Autowired
	protected TransactionUtil txUtil;
	
	private Integer employeeId;

	@Before
	public void persistInitialData() throws ParseException {
		Employee employee = new Employee("John");
		EmployeeDetails details = new EmployeeDetails(); 
		details.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"));
		
		employee.setDetails(details);
		details.setEmployee(employee); // SOLUTION
		
		txUtil.executeInSeparateTransaction(() -> {
			em.persist(employee);
		});
		
		employeeId = employee.getId(); // ID is set by JPA on entity at .persist() 
	}
	
	@Test
	public void initialDataWasCorrectlyPersisted() {
		Employee employee = em.find(Employee.class, employeeId);
		assertNotNull(employee.getDetails());
	}
	
	@Test
	public void changesAreFlushedToDBAtTransactionCOMMIT() {
		// FIRST ANIMATION IN PPT (happy flow)
		assertEquals("John", em.find(Employee.class, employeeId).getName());

		service.changeEmployeeSuccessfully(employeeId);

		assertEquals("NewName", em.find(Employee.class, employeeId).getName()); 
	}

	
	@Test
	public void changesAreLostAtTransactionROLLBACK() {
		// SECOND ANIMATION IN PPT (Exception thrown)
		assertEquals("John", em.find(Employee.class, employeeId).getName());

		try {
			service.changeEmployeeFailing(employeeId);
		} catch (RuntimeException e) {
			// expected exception. continue..
		}

		assertEquals("John", em.find(Employee.class, employeeId).getName());
	}
	

	@Test
	@Transactional
	public void persistenceContextPropagatesWithTheTransaction_actingLikeAnObjectCache() {
		Employee e1 = em.find(Employee.class, employeeId);
		Employee e2 = employeeRepo.getById(employeeId);
		
		// Persistence Context acts like a cache
		// Within the same transaction, for a given ID, it gives you the same Java INSTANCE
		
		assertTrue(e1 == e2);
	}
	
	
	@Test(expected=OptimisticLockException.class)
	@Transactional
	public void optimisticLockingWorks() {
		Employee employeeInVacation = em.find(Employee.class, employeeId);
		em.detach(employeeInVacation); // sent to Tahiti
		
		employeeInVacation.setName("new Name");
		
		// meanwhile, another client changes the same entity
		txUtil.executeInSeparateTransaction(() -> {
			Employee employee2 = em.find(Employee.class, employeeId);
			System.out.println("Before 1st change: @Version in DB = " + employee2.getVersion());
			employee2.setName("concurrent name");
		});
		
		System.out.println("Before 2nd change");
		System.out.println("@Version in DB = " + em.find(Employee.class, employeeId).getVersion());
		System.out.println("@Version in incoming data = " + employeeInVacation.getVersion());
		
		em.merge(employeeInVacation); // try to re-attach
	}
	
	@Test
	@Transactional
	public void manyToMany_linkTable_managedByTransparentlyByJpa() {
		txUtil.executeInSeparateTransaction(() -> {
			Employee employee = em.find(Employee.class, employeeId);
			
			Project project = new Project("Colibri Project");
			project.getEmployees().add(employee);
			em.persist(project);
		});
		
		Employee employee = em.find(Employee.class, employeeId);
		System.out.println("Projects of employee: " + employee.getProjects());
		assertEquals(1, employee.getProjects().size());
		
		Project project = employee.getProjects().get(0);
		System.out.println("Employees of project: " + project.getEmployees());
		assertEquals(1, project.getEmployees().size());
	}

}
