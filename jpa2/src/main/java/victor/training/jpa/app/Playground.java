package victor.training.jpa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.sql.DataSource;

@Service
public class Playground {
    public static final Logger log = LoggerFactory.getLogger(Playground.class);
    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private DataSource ds;

    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");
        teacherRepo.findAll();
//        new RuntimeException().printStackTrace();
//        secondTransaction();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");
    }
}


//@Aspect
//    @Order(1) // runs BEFORE the TxInterceptor
//@Component
//class Test {
//    @Around("execution(* Playground.*(..))")
//    public Object intercept(ProceedingJoinPoint point) throws Throwable {
//        System.out.println("NOW");
//        return point.proceed();
//    }
//
//}