package victor.training.jpa.play;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.OptimisticLockException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.ManagedJPABaseTestBase;
import victor.training.jpa.model.employee.Company;
import victor.training.jpa.model.employee.Employee;
import victor.training.jpa.model.employee.EmployeeDetails;

/** !! Look closely !! */
public class MasterPlay extends ManagedJPABaseTestBase {

	private Date date;
	private Employee employeeInDB;
	public Company companyInDB;

	@Before
	public void initPersistCompany() {
		companyInDB = new Company();
		companyInDB.getEmployees().add(new Employee("John"));
		companyInDB.getEmployees().add(new Employee("Mike"));

		createCompanyInSeparateTransaction(companyInDB);
	}
	
	@Before
	public void initPersistEmployee() throws ParseException {
		employeeInDB = new Employee("John");
		EmployeeDetails details = new EmployeeDetails(); 
		date = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01");
		details.setStartDate(date);
		
		employeeInDB.setDetails(details);
		details.setEmployee(employeeInDB);
		
		persistInSeparateTransaction(employeeInDB);
	}

	
	@Test
	public void changesAreFlushedToDBAtTransactionCOMMIT() {
		// FIRST ANIMATION IN PPT (happy flow)
		employeeInDB = em.find(Employee.class, employeeInDB.getId());
		assertEquals("The initial name is 'John'", "John", employeeInDB.getName());

		service.changeEmployeeSuccessfully(employeeInDB.getId());

		assertEquals("The new name was stored in DB", "NewName", em.find(Employee.class, employeeInDB.getId()).getName());
	}

	
	@Test
	public void changesAreDiscardedForTransactionROLLBACK() {
		// SECOND ANIMATION IN PPT (Exception thrown)
		assertEquals("The initial name is 'John'", "John", em.find(Employee.class, employeeInDB.getId()).getName());

		try {
			service.changeEmployeeFailing(employeeInDB.getId());
		} catch (RuntimeException e) {
			// expected exception
		}

		assertEquals("The new name was NOT persisted (Tx failed)", "John", em.find(Employee.class, employeeInDB.getId()).getName());
	}
	

	@Test
	@Transactional
	public void persistenceContextPropagatesWithTheTransaction_actingLikeAnObjectCache() {
		Integer employeeId = employeeInDB.getId();
		Employee e1 = em.find(Employee.class, employeeId);
		Employee e2 = dao.getById(employeeId);
		
		// Persistence Context acts like a cache
		// Within the same transaction, for a given ID, it gives you the same Java INSTANCE
		
		assertTrue(e1 == e2);
	}
	
	
	@Test(expected=OptimisticLockException.class)
	@Transactional
	public void optimisticLockingWorks() {
		Employee employeeInVacation = em.find(Employee.class, employeeInDB.getId());
		em.detach(employeeInVacation); // sent to Tahiti
		
		employeeInVacation.setName("new Name");
		
		// meanwhile, another client changes the same entity
		txUtil.executeInSeparateTransaction(new Runnable() {
			@Override
			public void run() {
				Employee employee2 = em.find(Employee.class, employeeInDB.getId());
				employee2.setName("concurrent name");
			}
		});
		
		em.merge(employeeInVacation); // try to re-attach
	}
	

}
