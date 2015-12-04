package victor.training.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import victor.training.jpa.dao.EmployeeDAO;
import victor.training.jpa.model.employee.Company;
import victor.training.jpa.service.HRService;
import victor.training.jpa.util.TransactionUtil;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class ManagedJPABaseTestBase {

	@PersistenceContext
	protected EntityManager em;

	@Autowired
	protected EmployeeDAO dao;

	@Autowired
	protected HRService service;

	@Autowired
	protected TransactionUtil txUtil;

	protected void persistInSeparateTransaction(final Object target) {
		txUtil.executeInSeparateTransaction(new Runnable() {
			@Override
			public void run() {
				em.persist(target);
			}
		});
	}
	
	protected void executeInSeparateTransaction(Runnable runnable) {
		txUtil.executeInSeparateTransaction(runnable);
	}

	protected void createCompanyInSeparateTransaction(final Company persistedCompany) {
		txUtil.executeInSeparateTransaction(new Runnable() {
			@Override
			public void run() {
				service.createCompany(persistedCompany);
			}
		});
	}

}
