package victor.training.jpa.app.repo;

import java.util.List;

import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.TeachingActivity;
import victor.training.jpa.app.facade.dto.ActivitySearchCriteria;

public interface TeacherRepoCustom {
	List<Teacher> getAllTeachersForYear(long yearId);
	
	List<TeachingActivity> searchActivity(ActivitySearchCriteria criteria);
}
