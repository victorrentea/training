package victor.training.jpa.play;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.TypedQuery;

import org.hibernate.LazyInitializationException;
import org.junit.Test;

import victor.training.jpa.ManualJPABaseTestBase;
import victor.training.jpa.model.employee.Employee;
import victor.training.jpa.model.employee.EmployeeDetails;

public class DetachTest extends ManualJPABaseTestBase {

	@Test
	public void dummy() {
		em.find(Employee.class, employeeInDB.getId());
	}

	@Test
	public void changesToAnEntityInsideATransactionAreAutomaticallyFlushedToTheDBAtTransactionCommit() {
		startTransaction();
		Employee e = em.find(Employee.class, employeeInDB.getId());
		assertTrue("Entity is attached", em.contains(e));
		e.setName("NewName");
		commitTransaction();

		// sometime after...
		Employee anotherEntity = usingANewEntityManager().find(Employee.class, employeeInDB.getId());
		assertEquals("NewName", anotherEntity.getName());
	}
	
	
	@Test
	public void changesToAnEntityAfterTheTxEndAreNotPersistedInTheDB() {
		startTransaction();
		Employee employee = em.find(Employee.class, employeeInDB.getId());
		employee.setName("Attached");
		commitTransaction();

		employee.setName("Detached"); // will run, but the new value won't get persisted in DB

		// sometime after...
		Employee anotherEntity = usingANewEntityManager().find(Employee.class, employeeInDB.getId());
		assertEquals("Attached", anotherEntity.getName());
	}

	@Test
	public void cascadingAndOwnerSideWorks() throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2010-08-01");
		Employee employee = new Employee();
		EmployeeDetails employeeDetails = new EmployeeDetails();
		employeeDetails.setStartDate(date);
		employee.setDetails(employeeDetails);

		// TODO don't forget to set the owner side
		employeeDetails.setEmployee(employee); // SOLUTION

		
		startTransaction();
		em.persist(employee);
		// TODO to fix the error you get, you should either persist EmpDetails also or enable PERSIST cascading on the relation
		commitTransaction();
		
		Employee fromDB = usingANewEntityManager().find(Employee.class, employee.getId());
		assertNotNull(fromDB.getDetails());
		assertEquals(date, fromDB.getDetails().getStartDate());
	}
	
	@Test
	public void listsAreLazilyLoadedWithinATransaction() {
		startTransaction();
		Employee e = em.find(Employee.class, employeeInDB.getId());
		System.out.println("*** Before requesting the projects list");
		assertEquals(2, e.getProjects().size());
		System.out.println("*** After Hibernate has gone to DB to lazy load the list of projects");
		commitTransaction();
	}

	@Test(expected = LazyInitializationException.class)
	public void lazyLoadingOutOfTransactionFailsWithException() {
		startTransaction();
		Employee e = em.find(Employee.class, employeeInDB.getId());
		commitTransaction();
		e.getProjects().size(); // this causes an exception, as Tx had ended
	}

	@Test
	public void fetchJoinPreloadsTheChildrensUpFront() {
		startTransaction();
		TypedQuery<Employee> query = em.createNamedQuery("getWithProjects", Employee.class);
		query.setParameter("id", employeeInDB.getId());
		Employee employee = query.getSingleResult();
		commitTransaction();
		
		System.out.println(employee.getProjects().size()); // this won't crack, the list was explicitly eagerly JOIN FETCHED 
	}
	
	
}
