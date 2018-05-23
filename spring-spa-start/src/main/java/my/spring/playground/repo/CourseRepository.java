package my.spring.playground.repo;

import org.springframework.stereotype.Repository;

import my.spring.playground.domain.Course;

@Repository
public class CourseRepository extends BaseRepository<Course> {

	public Course getByName(String name) {
		for (Course c:map.values()) {
			if (name.equals(c.getName())) return c;
		}
		return null;
	}
	
}
