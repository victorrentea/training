package ejb.container;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRequiredException;
import javax.persistence.EntityManager;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ejb.EJBTest;
import ejb.container.tx.EmployeeDAO;
import ejb.container.tx.EmployeeDAOBean;
import ejb.container.tx.HRService;
import ejb.container.tx.NotificationDAOBean;
import ejb.container.tx.model.Employee;

@RunWith(Arquillian.class)
public class TransactionExerciseTest extends EJBTest {

	@EJB
	private HRService service;
	
	@EJB
	private EmployeeDAO employeeDAO;
	
	private EntityManager employeeEM = mock(EntityManager.class);
	private EntityManager notificationEM = mock(EntityManager.class);

	@Before
	public void setupMockEntityManagers() {
		EmployeeDAOBean.em = employeeEM;
		NotificationDAOBean.em = notificationEM;
	}
	
	@Test
	public void testCreate() {
		doAnswer(executesInValidTransaction()).when(employeeEM).persist(anyObject());
		service.createEmployee(new Employee());
	}
	
	@Test(expected = EJBTransactionRequiredException.class)
	public void testPersistWithNoTx() {
		employeeDAO.persist(new Employee());
	}
	
	@Test
	public void testGetEmployeeById() {
		doAnswer(executesWithoutTransaction()).when(employeeEM).find(Employee.class, "employeeId");
		service.getEmployeeById("employeeId");
	}
	
	@Test
	public void testSwitchPhones() {
		doAnswer(executesInValidTransaction("EmployeeDAO")).when(employeeEM).find(Employee.class, "e1id");
		doThrow(IllegalArgumentException.class).when(employeeEM).find(Employee.class, "e2id");
		doAnswer(executesInValidTransaction("NotificationDAO")).when(notificationEM).persist(anyObject());
		service.switchPhones("e1id", "e2id");
	}

	
}
