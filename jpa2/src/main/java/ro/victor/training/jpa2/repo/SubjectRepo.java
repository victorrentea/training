package ro.victor.training.jpa2.repo;

import java.util.List;

import ro.victor.training.jpa2.common.data.EntityRepository;
import ro.victor.training.jpa2.domain.entity.Subject;

public interface SubjectRepo extends EntityRepository<Subject, Long> {

	public Subject getByName(String name);
	
	public List<Subject> getByHolderTeacherName(String teacherName);
	
	public List<Subject> getByActiveTrue();
}
