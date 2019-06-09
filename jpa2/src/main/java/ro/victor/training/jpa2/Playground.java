package ro.victor.training.jpa2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.victor.training.jpa2.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

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
//    @Around("execution(* ro.victor.training.jpa2.Playground.*(..))")
//    public Object intercept(ProceedingJoinPoint point) throws Throwable {
//        System.out.println("NOW");
//        return point.proceed();
//    }
//
//}