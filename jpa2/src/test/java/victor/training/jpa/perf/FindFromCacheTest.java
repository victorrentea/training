//package victor.training.jpa.perf;
//
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//@Rollback(false)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class FindFromCacheTest {
//    private static final Logger log = LoggerFactory.getLogger(FindFromCacheTest.class);
//
//    {
//        = find(Customer.class, 1L);
//        = find(Customer.class, 2L);
//        = find(Customer.class, 3L);
//        = SELECT c FROM CUstomer
//
//
//            // in the same thread, 12 classes far away, in the same Tx (same Persisnce Context):
//        // it's faster to do. Will serve it from CACHE
//        c = .find(Customer.class, 17L);
//        if (c.isDisabled()) {
//            stuff
//        }
//        // than --> this will GO to the DB
//        c = SELECT c FROM Customer c where id=1 AND idDisabled = true
//
//    }
//
//}
