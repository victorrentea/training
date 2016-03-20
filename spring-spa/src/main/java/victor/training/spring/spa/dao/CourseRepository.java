package victor.training.spring.spa.dao;

import org.springframework.stereotype.Repository;

import victor.training.spring.spa.domain.Course;

@Repository
public class CourseRepository extends BaseRepository<Course> {

	public Course getByName(String name) {
		for (Course c:map.values()) {
			if (name.equals(c.getName())) return c;
		}
		return null;
	}
	
}
