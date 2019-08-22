package victor.training.jpa.perf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class NPlusOneTest {

	private static final Logger log = LoggerFactory.getLogger(NPlusOneTest.class);

	@Autowired
	private EntityManager em;

	@Before
	public void persistData() {
		em.persist(new Parent("Victor")
				.addChild(new Child("Emma"))
				.addChild(new Child("Vlad"))
		);
		em.persist(new Parent("Peter")
				.addChild(new Child("Maria"))
				.addChild(new Child("Stephan"))
				.addChild(new Child("Paul"))
		);
		TestTransaction.end();
		TestTransaction.start();
	}

	@Test
	public void nPlusOne() {
		Set<Parent> parents = parentRepo.getAllFetchingChildren();
		log.info("Parents Evening: all gathered");
		List<String> totalChildren = anotherMethod(parents);
		log.info("All children counted");
		assertThat(totalChildren).containsExactlyInAnyOrder("Vlad", "Emma", "Maria", "Paul", "Stephan");
		log.debug("rez = " + totalChildren);
	}
//	@Test
//	public void joins() {
//		// TODO
//	}
//	@Test
//	public void eager() {
//		// TODO + debate
//	}



	@Autowired
	private ParentRepo parentRepo;
	@Autowired
	private ChildrenRepo childrenRepo;

	private List<String> anotherMethod(Collection<Parent> parents) {
		log.debug("Start iterating over {} parents: {}", parents.size(), parents);
		List<String>  total = new ArrayList<>();
		for (Parent parent : parents) {
			List<String> myChildrenNames = parent.getChildren().stream().map(Child::getName).collect(Collectors.toList());
			total.addAll(myChildrenNames);
		}
		log.debug("Done counting: {} children", total);
		return total;
	}

}


interface ChildrenRepo extends JpaRepository<Child, Long> {
	@Query("SELECT c FROM Child c WHERE c.parent.id = ?1")
	Set<Child> xxgetByParentId(long parentId);
}

interface ParentRepo extends JpaRepository<Parent, Long> {
	@Query("FROM Parent p LEFT JOIN FETCH p.children")
	Set<Parent> getAllFetchingChildren();
}