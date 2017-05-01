package victor.training.jpa.entity;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class DummyDataCreator {
	
	private final static Logger log = LoggerFactory.getLogger(DummyDataCreator.class);
	
	private TransactionTemplate txTemplate;
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		txTemplate = new TransactionTemplate(transactionManager);
	}
	
	@PostConstruct
	public void insertInitialDummyData() {
		txTemplate.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus status) {
				if (em.createQuery("SELECT COUNT(e) FROM Employee e", Long.class).getSingleResult() > 0) {
					log.info("Database already initialized (found one Employee). Skip inserting  ...");
				} else {
					log.info("---------------- INSERT DUMMY DATA-----------------------");
					insertEntities();
					log.info("---------------- DONE -----------------------");
				}
				return null;
			}
		});

	}

	private void insertEntities() {
		Company c1 = new Company();
		c1.setName("Kepler");
		em.persist(c1);
		
		Site s1 = new Site();
		s1.setName("Dimitrie Pompei");
		s1.setCompany(c1);
		em.persist(s1);
		
		
		
		Employee e1 = new Employee();
		e1.setName("John");
		e1.setCompany(c1);
		c1.getEmployees().add(e1);
		e1.setSite(s1);
		s1.getEmployees().add(e1);
		EmployeeDetails e1d = new EmployeeDetails();
		e1d.setStartDate(new Date());
		e1.setDetails(e1d);
		e1d.setEmployee(e1);
		em.persist(e1d);
		em.persist(e1);
		
		
		Project p1 = new Project();
		p1.setName("CORE");
		p1.setType(ProjectType.PRIVATE);
		// p1.getEmployees().add(e1); //INITIAL
		p1.addEmployee(e1);// SOLUTION
		em.persist(p1);
	}

}
