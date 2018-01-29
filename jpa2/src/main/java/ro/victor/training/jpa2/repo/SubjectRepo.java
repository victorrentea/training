package ro.victor.training.jpa2.repo;

import org.springframework.stereotype.Repository;

import ro.victor.training.jpa2.common.data.EntityRepository;
import ro.victor.training.jpa2.domain.entity.Subject;

@Repository
public interface SubjectRepo extends EntityRepository<Subject, Long> {

}
