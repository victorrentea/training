package victor.training.spring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import victor.training.spring.dao.NotificationDAO;
import victor.training.spring.domain.Employee;
import victor.training.spring.test.util.TransactionUtil;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeServiceTest {

	@Autowired
	private EmployeeService service;
	
	@Autowired
	private NotificationDAO notificationDao;
	
	@Autowired
	private SillyBatch batch;
	
	@Autowired
	private TransactionUtil txUtil;
	
	@Autowired
	private JdbcTemplate jdbc;

	private Employee emp1 = new Employee("John", "000");
	
	@Before
	public void setupInitialData() {
		service.createEmployee(emp1);
		System.out.println("COUNT(EMPLOYEE) = " + jdbc.queryForObject("SELECT COUNT(*) FROM Employee", Integer.class));
	}
	
	@Test
	@Transactional
	public void getEmployeeById() {
		service.getEmployeeById(emp1.getId());
	}
	
	@Test
	@Transactional
	public void updatePhone_different_ok() {
		service.updateEmployeePhone(emp1.getId(), "newPhone");
		assertEquals("newPhone", service.getEmployeeById(emp1.getId()).getPhone());
	}
	
	@Test
	@Transactional
	public void updatePhone_same_ok() {
		assertEquals(1, (int) jdbc.queryForObject("SELECT COUNT(*) FROM EMPLOYEE", Integer.class));
		
		service.updateEmployeePhone(emp1.getId(), "000");
		assertEquals("000", service.getEmployeeById(emp1.getId()).getPhone());
	}
	
	@Test
	@DirtiesContext
	public void updatePhone_different_duplicate_KO() {
		Employee emp2 = new Employee("Doe", "111");
		service.createEmployee(emp2);
		
		try {
			batch.updateManyPhones(Arrays.asList(emp1.getId()), Arrays.asList("111"));
			fail("Expected exception");
		} catch (IllegalArgumentException e) {
			//expected
			e.printStackTrace();
		}
		assertFalse("The nested Tx must have ended, for me to see real DB after", txUtil.hasTransaction());
		assertEquals("The change was ROLLBACKed", "000", service.getEmployeeById(emp1.getId()).getPhone());
		assertTrue("Notification actually persisted", notificationDao.getCount() > 0);	
	}
	
}
