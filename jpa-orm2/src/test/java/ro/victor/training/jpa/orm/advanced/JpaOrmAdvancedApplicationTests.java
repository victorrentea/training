package ro.victor.training.jpa.orm.advanced;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ro.victor.training.jpa.orm.advanced.entity.Floor;
import ro.victor.training.jpa.orm.advanced.entity.FloorPK;
import ro.victor.training.jpa.orm.advanced.entity.Iteration;
import ro.victor.training.jpa.orm.advanced.entity.NoPK;
import ro.victor.training.jpa.orm.advanced.entity.Project;
import ro.victor.training.jpa.orm.advanced.repo.ProjectOverviewRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaOrmAdvancedApplicationTests {
	@Autowired
	private EntityManager em;
	
	@Autowired
	private ProjectOverviewRepository projectOverviewRepo; 

	@Test
	@Transactional
	@Rollback(false)
	public void contextLoads() {
		Project p = new Project();
		p.setName("n");
		Iteration i = new Iteration();
		i.setIteration(1);
		i.setProject(p);
		p.getIterations().add(i);
		em.persist(p);
		em.persist(i);
//		em.persist(new Project());
		
		
		Floor floor = new Floor();
		floor.setId(new FloorPK("GDC",1));
		floor.setCapacity(300);
		em.persist(floor);
		
		
		em.flush(); // needed to flush to DB - JPA couldn't know that ProjectOverview is a view
		System.out.println(projectOverviewRepo.findAll());
	}
	
	@Test
	public void testCallProcedure() {
		System.out.println(projectOverviewRepo.procedure_test(1));
	}
	
	@Test
	public void noPK() {
		List<NoPK> entities = em.createQuery("FROM NoPK", NoPK.class).getResultList();
		System.out.println(entities);
	}

}
