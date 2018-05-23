package my.spring.playground.service;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.spring.playground.domain.Course;
import my.spring.playground.domain.Teacher;
import my.spring.playground.repo.CourseRepository;
import my.spring.playground.repo.TeacherRepository;

@Service
public class DummyData {

	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private TeacherRepository teacherRepo;

	@PostConstruct
	public void initMockData() {
		Course c1 = new Course("Spring Framework", "All about Spring", new Date(System.currentTimeMillis()+10*24*60*60*1000L));
		Course c2 = new Course("JPA", "The coolest standard in Java EE", new Date(System.currentTimeMillis()+2*24*60*60*1000L));
		Course c3 = new Course("AngularJS", "The new way of doing Single Page Applications", new Date(System.currentTimeMillis()+20*24*60*60*1000L));
		courseRepo.save(c1);
		courseRepo.save(c2);
		courseRepo.save(c3);
		
		Teacher t1 = new Teacher("Victor");
		Teacher t2 = new Teacher("Dragos");
		teacherRepo.save(t1);
		teacherRepo.save(t2);
		
		c1.setTeacher(t1);t1.getCourses().add(c1);
		c2.setTeacher(t1);t1.getCourses().add(c2);
		c3.setTeacher(t2);t2.getCourses().add(c3);
		
	}
	
	
}
