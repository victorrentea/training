package victor.training.spring.spa.service;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import victor.training.spring.spa.dao.CourseRepository;
import victor.training.spring.spa.dao.StudentRepository;
import victor.training.spring.spa.dao.TeacherRepository;
import victor.training.spring.spa.domain.Course;
import victor.training.spring.spa.domain.Student;
import victor.training.spring.spa.domain.Teacher;

@Service
public class CoursesService {

	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private StudentRepository studentRepo;

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
		
		Student s1 = new Student("Hercule");
		Student s2 = new Student("Artemis");
		Student s3 = new Student("Hera");
		Student s4 = new Student("Afrodita");
		Student s5 = new Student("Hefaistos");
		studentRepo.save(s1);
		studentRepo.save(s2);
		studentRepo.save(s3);
		studentRepo.save(s4);
		studentRepo.save(s5);
		
		enroll(s1,c1);
		enroll(s2,c1);
		enroll(s3,c1);
		enroll(s1,c2);
		enroll(s2,c2);
		enroll(s3,c3);
		enroll(s4,c3);
		enroll(s5,c3);
	}
	
	public void enroll(Student s, Course c) {
		c.getStudents().add(s);
		s.getCourses().add(c);
	}
	
}
