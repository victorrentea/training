package victor.training.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.persistence.TypedQuery;

import org.hibernate.LazyInitializationException;
import org.junit.Test;

import victor.training.jpa.entity.employee.Employee;
import victor.training.jpa.test.util.ManualTxTestBase;

public class ManualTxTest extends ManualTxTestBase {

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
		
		// @NamedQueries are validated by Hibernate at deploy
		TypedQuery<Employee> query = em.createNamedQuery("getWithProjects", Employee.class); 
		
//		String jpql = "SELECT e FROM Employee e JOIN FETCH e.projects WHERE e.id = :id";
//		TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
		
		query.setParameter("id", employeeInDB.getId());
		Employee employee = query.getSingleResult();
		commitTransaction();
		
		System.out.println(employee.getProjects().size()); // this won't crack, the list was explicitly eagerly JOIN FETCHED 
	}
	
	
}
