package spring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.NotificationDAO;
import spring.model.Employee;
import spring.test.util.TransactionUtil;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class EmployeeServiceTest {

	@Autowired
	private HRService employeeService;
	
	@Autowired
	private NotificationDAO notificationDao;
	
	@Autowired
	private TransactionUtil txUtil;

	private Employee inDatabase = new Employee("Doe", "111");

	@Before
	public void setupInitialData() {
		employeeService.createEmployee(inDatabase);
	}

	@Test
	public void testGetEmployeeById() {
		employeeService.getEmployeeById(inDatabase.getId());
	}
	
	@Test
	public void testSwitchPhoneSuccessful() {
		Employee e2 = new Employee("Mike", "000");
		employeeService.createEmployee(e2);
		
		employeeService.switchPhones(inDatabase.getId(), e2.getId());
		
		assertEquals("000", employeeService.getEmployeeById(inDatabase.getId()).getPhone());
		assertEquals("111", employeeService.getEmployeeById(e2.getId()).getPhone());
	}
	
	@Test
	@Transactional
	public void testUpdatePhoneFailsAndNotificationIsSent() {
		employeeService.switchPhones("unexistent id", "000");
		assertTrue("Transaction is marked for rollback", txUtil.isTransactionRollbacked());
		assertEquals("111", employeeService.getEmployeeById(inDatabase.getId()).getPhone());
		
		txUtil.executeWith_NOT_SUPPORTED(new Runnable(){
			@Override
			public void run() {
				assertTrue("Notification actually persisted", notificationDao.getCount() > 0);				
			}
		});
	}
}
