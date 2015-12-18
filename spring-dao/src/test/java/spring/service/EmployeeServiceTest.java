package spring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
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
public class EmployeeServiceTest {

	@Autowired
	private EmployeeService hrService;
	
	@Autowired
	private NotificationDAO notificationDao;
	
	@Autowired
	private TransactionUtil txUtil;

	private Employee inDatabase = new Employee("Doe", "111");

	@Before
	public void setupInitialData() {
		hrService.createEmployee(inDatabase);
	}
	
	@Test
	public void testTransactionLocal() {
		hrService.m1();
	}

	@Test
	public void testGetEmployeeById() {
		hrService.getEmployeeById(inDatabase.getId());
	}
	
	@Test
	public void testSwitchPhoneSuccessful() {
		Employee e2 = new Employee("Mike", "000");
		hrService.createEmployee(e2);
		
		hrService.switchPhones(inDatabase.getId(), e2.getId());
		
		assertEquals("000", hrService.getEmployeeById(inDatabase.getId()).getPhone());
		assertEquals("111", hrService.getEmployeeById(e2.getId()).getPhone());
	}
	
	@Test
	@Transactional
	public void testUpdatePhoneFailsAndNotificationIsSent() {
		hrService.switchPhones("unexistent id", "000");
		assertTrue("Transaction is marked for rollback", txUtil.isTransactionRollbacked());
		assertEquals("111", hrService.getEmployeeById(inDatabase.getId()).getPhone());
		
		txUtil.executeWith_NOT_SUPPORTED(new Runnable(){
			@Override
			public void run() {
				assertTrue("Notification actually persisted", notificationDao.getCount() > 0);				
			}
		});
	}
}
