package spring.dao;

import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unitils.reflectionassert.ReflectionAssert;

import spring.model.Employee;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public abstract class EmployeeDAOTest {
	
	private Employee inDatabase = new Employee("Doe", "111");
	
	@Before
	public void setupInitialData() {
		getDao().persist(inDatabase);
	}

	@Test
	public void testGetByIdAfterPersistEmployeeReturnsTheSameEmployee() {
		Employee fromDB = getDao().getById(inDatabase.getId());

		ReflectionAssert.assertReflectionEquals(inDatabase, fromDB);
	}

	@Test
	public void testGetAfterUpdateReturnsDataChanges() {
		
		inDatabase.setName("John");
		getDao().update(inDatabase);

		Employee fromDB = getDao().getById(inDatabase.getId());

		Assert.assertNotNull(fromDB);
		Assert.assertEquals("John", fromDB.getName());
	}
	
	
	@Ignore
	@Test(expected = DataRetrievalFailureException.class)
	public void testGetByUnexistentIdThrowsDataRetrievalFailureException() {
		getDao().getById("unexistent");
	}
	
	@Test
	public void testGetAfterRemoveReturnsNullEmployee() {
		getDao().getById(inDatabase.getId());

		getDao().removeById(inDatabase.getId());
		Employee employee = getDao().getById(inDatabase.getId());
		assertNull(employee);
	}
	
	@Ignore
	@Test(expected=DataRetrievalFailureException.class)
	public void testGetAfterRemoveFailsWithDataRetrievalFailureException() {
		getDao().getById(inDatabase.getId());

		getDao().removeById(inDatabase.getId());
	 	getDao().getById(inDatabase.getId());
	}
	protected abstract EmployeeDAO getDao();
	
}
