package victor.training.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.TypedQuery;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import victor.training.jpa.entity.employee.Employee;
import victor.training.jpa.service.EmployeeService;
import victor.training.jpa.test.util.ManualTxTestBase;

public class LazyEagerTest extends ManualTxTestBase {
	
	@Autowired
	private EmployeeService service;

	@Test
	public void dummy() {
		assertNotNull(entityManager.find(Employee.class, employeeId));
	}

	@Test
	public void changesToAnEntityInsideATransactionAreAutomaticallyFlushedToTheDBAtTransactionCommit() {
		startTransaction();
		Employee e = entityManager.find(Employee.class, employeeId);
		assertTrue("Entity is attached", entityManager.contains(e));
		e.setName("NewName");
		commitTransaction();

		// sometime after...
		Employee anotherEntity = usingANewEntityManager().find(Employee.class, employeeId);
		assertEquals("NewName", anotherEntity.getName());
	}
	
	
	@Test
	public void changesToAnEntityAfterTheTxEndAreNotPersistedInTheDB() {
		startTransaction();
		Employee employee = entityManager.find(Employee.class, employeeId);
		employee.setName("Attached");
		commitTransaction();

		employee.setName("Detached"); // will run, but the new value won't get persisted in DB

		// sometime after...
		Employee anotherEntity = usingANewEntityManager().find(Employee.class, employeeId);
		assertEquals("Attached", anotherEntity.getName());
	}

	
	@Test
	public void listsAreLazilyLoadedWithinATransaction() {
		startTransaction();
		Employee e = entityManager.find(Employee.class, employeeId);
		System.out.println("*** Before requesting the projects list");
		assertEquals(2, e.getProjects().size());
		System.out.println("*** After Hibernate has gone to DB to lazy load the list of projects");
		commitTransaction();
	}

	@Test(expected = LazyInitializationException.class)
	public void lazyLoadingOutOfTransactionFailsWithException() {
		startTransaction();
		Employee e = entityManager.find(Employee.class, employeeId);
		commitTransaction();
		e.getProjects().size(); // this causes an exception, as Tx had ended
	}

	@Test
	public void fetchJoinPreloadsTheChildrensUpFront() {
		startTransaction();
		
		// @NamedQueries are validated by Hibernate at deploy
		TypedQuery<Employee> query = entityManager.createNamedQuery("Employee_getWithProjects", Employee.class); 
		
//		String jpql = "SELECT e FROM Employee e JOIN FETCH e.projects WHERE e.id = :id";
//		TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
		
		query.setParameter("id", employeeId);
		Employee employee = query.getSingleResult();
		commitTransaction();
		
		System.out.println(employee.getProjects().size()); // this won't crack, the list was explicitly eagerly JOIN FETCHED 
	}
	
	
	@Test
	public void lazilyLoadToOneFieldUsingBytecodeEnhancement() {
		startTransaction();
		Employee e = entityManager.find(Employee.class, employeeId);
		System.out.println("*** Before requesting the employee company");
		System.out.println("employee.company.class = " + e.getCompany().getClass());
		System.out.println("employee.company.toString() = " + e.getCompany());
		System.out.println("*** After requesting the employee company");
		commitTransaction();
	}
	
	
	
}
