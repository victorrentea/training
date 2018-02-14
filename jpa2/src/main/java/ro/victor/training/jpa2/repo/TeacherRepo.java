package ro.victor.training.jpa2.repo;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ro.victor.training.jpa2.common.data.EntityRepository;
import ro.victor.training.jpa2.domain.entity.Subject;
import ro.victor.training.jpa2.domain.entity.Teacher;

public interface TeacherRepo extends EntityRepository<Teacher, Long>, TeacherRepoCustom {

	@Query("SELECT DISTINCT a.day FROM Teacher t JOIN t.activities a WHERE t.id=?1")
	public Set<DayOfWeek> getBusyDaysOfTeacher(long teacherId);
	
	@Query("SELECT DISTINCT a.subject FROM Teacher t JOIN t.activities a WHERE t.id=?1")
	public Set<Subject> getSubjectsKnownByTeacher(long teacherId);

	@Query("SELECT DISTINCT t FROM TeachingActivity a JOIN a.teachers t "
			+ "WHERE a.group.id =?1 or a.year.id=(select g.year.id from StudentsGroup g where g.id=?1)")
	public Set<Teacher> getTeachersKnownByGroup(long groupId);
	
	@Query("SELECT DISTINCT a.subject FROM TeachingActivity a WHERE a.roomId=?1")
	public Set<Subject> getSubjectsInRoom(String roomId);

	public Optional<Teacher> findByName(String name);

}
