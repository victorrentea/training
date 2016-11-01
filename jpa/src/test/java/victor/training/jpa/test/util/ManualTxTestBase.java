package victor.training.jpa.test.util;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import victor.training.jpa.entity.employee.Employee;
import victor.training.jpa.entity.employee.Project;
import victor.training.jpa.entity.employee.ProjectType;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class ManualTxTestBase {
	
	@PersistenceUnit
	protected EntityManagerFactory emFactory;
	
	protected EntityManager em;

	protected Employee employeeInDB = new Employee("inDatabase");
	
	@Before
	public void setupInitialEM() {
		usingANewEntityManager();
	}
	
	public EntityManager usingANewEntityManager() {
		return em = emFactory.createEntityManager();
	}
	
	protected void startTransaction() {
		em.getTransaction().begin();
	}

	protected void commitTransaction() {
		em.getTransaction().commit();
		em.close(); // in a regular EE setup, the EntityManager is closed automatically
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
		em.persist(employeeInDB);
		em.persist(newProjectWithEmployee(employeeInDB));
		em.persist(newProjectWithEmployee(employeeInDB));
		commitTransaction();
		usingANewEntityManager();
	}
}
