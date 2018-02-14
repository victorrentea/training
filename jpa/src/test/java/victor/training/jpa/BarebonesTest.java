package victor.training.jpa;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.entity.employee.Company;
import victor.training.jpa.entity.employee.Employee;
import victor.training.jpa.entity.employee.EmployeePhone;
import victor.training.jpa.entity.employee.EmployeePhone.Type;
import victor.training.jpa.test.util.TransactionUtil;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("hsql")
public class BarebonesTest {

	@Autowired
	private TransactionUtil txUtil;
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Test
	public void childrenOrder() throws Exception {
		int eid = txUtil.executeInSeparateTransaction(() -> {
			Employee e = new Employee();
			EmployeePhone phone1 = new EmployeePhone("111", Type.HOME);
			e.getPhones().add(phone1);
			phone1.setEmployee(e);
			entityManager.persist(e);
			return e.getId();
		});
		
		txUtil.executeInSeparateTransaction(() -> {
			Employee e = entityManager.find(Employee.class, eid);
			EmployeePhone phone0 = new EmployeePhone("000", Type.OFFICE);
			e.getPhones().add(0, phone0); // Try to add as first in list
			phone0.setEmployee(e);
			return e.getId();
		});
		
		txUtil.executeInSeparateTransaction(() -> {
			Employee e = entityManager.find(Employee.class, eid);
			assertEquals("000", e.getPhones().get(0).getPhoneNumber());
		});
		
		// TODO: try @OrderBy, @OrderColumn
		// --> do they work on ManyToMany also ?
		
		// TODO: what about not allowing duplicates ?
		// ---> How to implement correctly HashCode and Equals?
	}
	
	@Test(expected = PersistenceException.class)
	@Transactional
	public void cannotInsertTwoCompaniesWithTheSameName() {
		entityManager.persist(new Company("A"));
		entityManager.persist(new Company("A"));
	}
	
	// TODO make countryIso a FK to a Country label
}
