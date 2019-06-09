package ro.victor.training.jpa2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.victor.training.jpa2.repo.TeacherRepo;

import javax.persistence.EntityManager;

@Service
public class Playground {
    public static final Logger log = LoggerFactory.getLogger(Playground.class);
    @Autowired
    private TeacherRepo teacherRepo;

    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");
        teacherRepo.findAll();
    }

    @Transactional
    public void secondTransaction() {
        log.debug("Halo!");
    }
}
