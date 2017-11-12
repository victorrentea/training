package victor.training.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.entity.employee.Address;
import victor.training.jpa.entity.employee.Company;
import victor.training.jpa.entity.employee.Employee;
import victor.training.jpa.entity.employee.EmployeeDetails;
import victor.training.jpa.entity.employee.EmployeePhone;
import victor.training.jpa.entity.employee.EmployeePhone.Type;
import victor.training.jpa.entity.employee.Project;
import victor.training.jpa.entity.employee.Site;
import victor.training.jpa.repository.EmployeeRepository;
import victor.training.jpa.service.EmployeeService;
import victor.training.jpa.test.util.TransactionUtil;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("hsql")
/** !! Look closely !! */
public class MasterPlay {
	@PersistenceContext
	protected EntityManager entityManager;
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
		
		EmployeePhone workPhone = new EmployeePhone("000-999-000", Type.OFFICE); 
		employee.getPhones().add(workPhone);
		workPhone.setEmployee(employee); // SOLUTION
		
		
		txUtil.executeInSeparateTransaction(() -> {
			entityManager.persist(employee);
		});
		
		employeeId = employee.getId(); // ID is set by JPA on entity at .persist() 
	}
	
	@Test
	@Transactional
	public void initialDataWasCorrectlyPersisted() {
		Employee employee = entityManager.find(Employee.class, employeeId);
		assertNotNull(employee.getDetails());
		assertEquals(1, employee.getPhones().size());
	}
	
	@Test
	public void changesAreFlushedToDBAtTransactionCOMMIT() {
		// FIRST ANIMATION IN PPT (happy flow)
		assertEquals("John", entityManager.find(Employee.class, employeeId).getName());

		service.changeEmployeeSuccessfully(employeeId);

		assertEquals("NewName", entityManager.find(Employee.class, employeeId).getName()); 
	}

	
	@Test
	public void changesAreLostAtTransactionROLLBACK() {
		// SECOND ANIMATION IN PPT (Exception thrown)
		assertEquals("John", entityManager.find(Employee.class, employeeId).getName());

		try {
			service.changeEmployeeFailing(employeeId);
		} catch (RuntimeException e) {
			// expected exception. continue..
		}

		assertEquals("John", entityManager.find(Employee.class, employeeId).getName());
	}
	

	@Test
	@Transactional
	public void persistenceContextPropagatesWithTheTransaction_actingLikeAnObjectCache() {
		Employee e1 = entityManager.find(Employee.class, employeeId);
		Employee e2 = employeeRepo.getById(employeeId);
		
		// Persistence Context acts like a cache
		// Within the same transaction, for a given ID, it gives you the same Java INSTANCE
		
		assertTrue(e1 == e2);
	}
	
	
	@Test(expected=OptimisticLockException.class)
	@Transactional
	public void optimisticLockingWorks() {
		Employee employeeInVacation = entityManager.find(Employee.class, employeeId);
		entityManager.detach(employeeInVacation); // sent to Tahiti
		
		employeeInVacation.setName("new Name");
		
		// meanwhile, another client changes the same entity
		txUtil.executeInSeparateTransaction(() -> {
			Employee employee2 = entityManager.find(Employee.class, employeeId);
			System.out.println("Before 1st change: @Version in DB = " + employee2.getVersion());
			employee2.setName("concurrent name");
		});
		
		System.out.println("Before 2nd change");
		System.out.println("@Version in DB = " + entityManager.find(Employee.class, employeeId).getVersion());
		System.out.println("@Version in incoming data = " + employeeInVacation.getVersion());
		
		entityManager.merge(employeeInVacation); // try to re-attach
	}
	
	@Test
	@Transactional
	public void manyToMany_linkTable_managedByTransparentlyByJpa() {
		txUtil.executeInSeparateTransaction(() -> {
			Employee employee = entityManager.find(Employee.class, employeeId);
			
			Project project = new Project("Colibri Project");
			project.getEmployees().add(employee);
			entityManager.persist(project);
		});
		
		Employee employee = entityManager.find(Employee.class, employeeId);
		System.out.println("Projects of employee: " + employee.getProjects());
		assertEquals(1, employee.getProjects().size());
		
		Project project = employee.getProjects().get(0);
		System.out.println("Employees of project: " + project.getEmployees());
		assertEquals(1, project.getEmployees().size());
	}

	@Test
	@Ignore("Takes too long - un-Ingore to test")
	@Transactional
	public void lazyLoadPerformance() throws IOException {
		for (int i=0;i<1000; i++) {
			Employee employee = new Employee("A"+i);
			entityManager.persist(employee);
			for (int j=0; j<6; j++) {
				Project project = new Project("P"+i+j);
				project.getEmployees().add(employee);
				employee.getProjects().add(project);
				entityManager.persist(project);
			}
		}
		entityManager.flush();
		entityManager.clear(); // clear away the first level cache
		System.out.println("Persisted all");

		long t0 = System.currentTimeMillis();
		
		StringWriter stringWriter = new StringWriter();
		// TODO change the repository to add LEFT JOIN FETCH e.projects
		service.generateExport(stringWriter); 
		
		long deltaMillis = System.currentTimeMillis() - t0;
		
		System.out.println("Took: " + deltaMillis + " ms");
	}
	
	@Test
	@Transactional
	public void searchBySiteNameWorks() {
		Employee employee = new Employee("John");
		Site site = new Site("London");
		employee.setSite(site);
		entityManager.persist(site);
		entityManager.persist(employee);
		
		// Look: how to compose a search query
		assertEquals(employee, employeeRepo.search(null, "London").get(0));
	}
	
	@Test
	@Transactional
	public void embeddables() {
		Company company = new Company("Kepler");
		company.getAddress().setCity("Bucharest"); // Look ma', Embeddables
		entityManager.persist(company);
		
	}
	
	@Test
	public void mergeChildrenEntities_reinsert() throws Exception {
		Integer oldChildId = txUtil.executeInSeparateTransaction(() -> {
			Employee employee = entityManager.find(Employee.class, employeeId);
			return employee.getPhones().get(0).getId();
		});
		System.out.println("Phone id before " + oldChildId);
		
		Employee newEmployee = new Employee("John");
		newEmployee.setId(employeeId);
		
		EmployeePhone newPhone = new EmployeePhone("000-999-000", Type.OFFICE); 
		newEmployee.getPhones().add(newPhone);
		newPhone.setEmployee(newEmployee); // SOLUTION
		// newPhone.setId(oldChildId); // what if...
	
		Integer newChildId = txUtil.executeInSeparateTransaction(() -> {
			Employee managedEmployee = entityManager.merge(newEmployee);
			assertEquals(1, managedEmployee.getPhones().size());
			return managedEmployee.getPhones().get(0).getId();
		});
		System.out.println("Phone id after " + newChildId);
		assertNull("Old ORPHAN child was removed", entityManager.find(EmployeePhone.class, oldChildId));
	}

	
	@Test
	public void mergeChildrenEmbeddables() throws Exception {
		txUtil.executeInSeparateTransaction(() -> {
			Employee employee = entityManager.find(Employee.class, employeeId);
			employee.getAddresses().add(new Address("Viorele 4", "000", "Bucharest", "RO"));
		});
		
		Employee newEmployee = new Employee("John");
		newEmployee.setId(employeeId);
		newEmployee.setVersion(1L);
		newEmployee.getAddresses().add(new Address("Viorele 5", "000", "Bucharest", "RO"));
	
		txUtil.executeInSeparateTransaction(() -> entityManager.merge(newEmployee));

		ArrayList<Address> addresses = txUtil.executeInSeparateTransaction(() -> new ArrayList<>(entityManager.find(Employee.class, employeeId).getAddresses()));
		
		assertEquals(1, addresses.size());
		assertEquals("Viorele 5", addresses.get(0).getStreet());
	}


	// TODO iff energy(trainee) > 0.000, then change to EmployeeDataRepository in the @Autowired at the top

}
