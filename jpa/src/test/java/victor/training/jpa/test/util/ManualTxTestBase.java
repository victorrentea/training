package victor.training.jpa.test.util;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import victor.training.jpa.entity.employee.Company;
import victor.training.jpa.entity.employee.Employee;
import victor.training.jpa.entity.employee.Project;
import victor.training.jpa.entity.employee.ProjectType;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class ManualTxTestBase {
	
	@PersistenceUnit
	protected EntityManagerFactory emFactory;
	
	protected EntityManager entityManager;

	protected Integer employeeId;
	
	
	@Before
	public void setupInitialEM() {
		usingANewEntityManager();
	}
	
	public EntityManager usingANewEntityManager() {
		return entityManager = emFactory.createEntityManager();
	}
	
	protected void startTransaction() {
		entityManager.getTransaction().begin();
	}

	protected void commitTransaction() {
		entityManager.getTransaction().commit();
		entityManager.close(); // in a regular EE setup, the EntityManager is closed automatically
	}
	
	protected void rollbackTransaction() {
		entityManager.getTransaction().rollback();
		entityManager.close(); // in a regular EE setup, the EntityManager is closed automatically
	}


	protected Project newProjectWithEmployee(Employee e) {
		Project p = new Project();
		p.setType(ProjectType.PRIVATE);
		p.setEmployees(Arrays.asList(e));
		e.getProjects().add(p);
		return p;
	}

	@Before
	public void persistInitialData() {
		usingANewEntityManager();
		startTransaction();
		Employee employeeInDB = new Employee("inDatabase");
		Company company = new Company("Microsoft SRL");
		employeeInDB.setCompany(company);
		entityManager.persist(company);
		entityManager.persist(employeeInDB);
		employeeId = employeeInDB.getId();
		entityManager.persist(newProjectWithEmployee(employeeInDB));
		entityManager.persist(newProjectWithEmployee(employeeInDB));
		commitTransaction();
		usingANewEntityManager();
	}
}
