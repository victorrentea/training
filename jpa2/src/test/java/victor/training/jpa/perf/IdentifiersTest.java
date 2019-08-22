package victor.training.jpa.perf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static java.lang.System.currentTimeMillis;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IdentifiersTest {
    private static final Logger log = LoggerFactory.getLogger(IdentifiersTest.class);

    @Autowired
    private EntityManager em;

    @Test
    public void assignIdentifiers() {
        long t0 = currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            em.persist(new IDDocument());
        }
        TestTransaction.end();
        long t1 = currentTimeMillis();
        log.debug("Took {} ms", t1 - t0);

        // TODO play with: Sequence size, IDENTITY, TABLE,
        // TODO show ID gaps
    }
}
