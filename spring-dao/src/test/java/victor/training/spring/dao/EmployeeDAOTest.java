package victor.training.spring.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import victor.training.spring.domain.Employee;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional // by default does rollback after each test
public class EmployeeDAOTest {
	
	private Employee employeeInDatabase = new Employee("Doe", "111");
	
	@Autowired
	private EmployeeDAO dao;
	
	@Autowired
	private JdbcTemplate jdbc;

	@Before
	public void setupInitialData() {
		dao.persist(employeeInDatabase);
		System.out.println("COUNT(EMPLOYEE) = " + jdbc.queryForObject("SELECT COUNT(*) FROM Employee", Integer.class));
	}

	@Test
	public void afterPersist_getReturnsTheSameEmployee() {
		Employee fromDB = dao.getById(employeeInDatabase.getId());

		assertEquals(employeeInDatabase.getName(), fromDB.getName());
		assertEquals(employeeInDatabase.getPhone(), fromDB.getPhone());
	}

	@Test
	public void afterUpdate_getReturnsUpdatedEmployee() {
		employeeInDatabase.setName("John");
		dao.update(employeeInDatabase);

		Employee fromDB = dao.getById(employeeInDatabase.getId());

		Assert.assertEquals("John", fromDB.getName());
	}
	
	@Test
	public void afterRemove_getReturnsNull() {
		assertNotNull(dao.findById(employeeInDatabase.getId()));

		dao.removeById(employeeInDatabase.getId());
		assertNull(dao.findById(employeeInDatabase.getId()));
	}
	
	
}
