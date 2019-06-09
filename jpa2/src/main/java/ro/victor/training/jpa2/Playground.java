package ro.victor.training.jpa2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.victor.training.jpa2.repo.TeacherRepo;

import javax.persistence.EntityManager;

@Service
@Slf4j
public class Playground {
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
