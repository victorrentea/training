package victor.training.jpa.app;

import static java.util.Arrays.asList;

import java.time.DayOfWeek;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.app.domain.entity.CourseActivity;
import victor.training.jpa.app.domain.entity.LabActivity;
import victor.training.jpa.app.domain.entity.StudentsGroup;
import victor.training.jpa.app.domain.entity.StudentsYear;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.TeacherDetails;
import victor.training.jpa.app.repo.TeacherRepo;

@Component
public class DummyDataCreator {

	private final static Logger log = LoggerFactory.getLogger(DummyDataCreator.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private TeacherRepo teacherRepo;
	
	@Transactional
	public void persistDummyData() {
		if (teacherRepo.findByName("Victor").isPresent()) {
			log.info("Skipping: Dummy data already in DB.YY");
			return;
		}
		Teacher victor = new Teacher("Victor");
		victor.setGrade(Teacher.Grade.ASSISTENT);
		TeacherDetails teacherDetails = new TeacherDetails().setCv("A pimped CV");
		victor.setDetails(teacherDetails);
		victor.setCounselingDay(DayOfWeek.MONDAY);
		victor.setCounselingDurationInHours(1);
		victor.setCounselingRoomId("EF403");
		victor.setCounselingStartHour(8);
		em.persist(victor);
		
		
		Teacher ionut = new Teacher("Ionut");
		ionut.setGrade(Teacher.Grade.ASSISTENT);
		em.persist(ionut);

		Teacher bianca = new Teacher("Bianca");
		bianca.setGrade(Teacher.Grade.ASSISTENT);
		em.persist(bianca);
		
		Subject subject = new Subject("OOP");
		subject.setHolderTeacher(victor);
		CourseActivity course = new CourseActivity();
		course.setSubject(subject);
		course.setDay(DayOfWeek.MONDAY);
		course.setStartHour(8);
		course.setDurationInHours(3);
		course.setRoomId("EC105");
		course.getTeachers().add(victor);
		
		LabActivity lab1 = new LabActivity();
		lab1.setSubject(subject);
		lab1.setDay(DayOfWeek.MONDAY);
		lab1.setStartHour(11);
		lab1.setDurationInHours(2);
		lab1.setRoomId("EC202");
		lab1.getTeachers().add(bianca);
		lab1.getTeachers().add(ionut);
		
		LabActivity lab2 = new LabActivity();
		lab2.setSubject(subject);
		lab2.setDay(DayOfWeek.TUESDAY);
		lab2.setStartHour(11);
		lab2.setDurationInHours(2);
		lab2.setRoomId("EC203");
		lab2.getTeachers().add(ionut);
		
		StudentsYear year = new StudentsYear("3CA");
		
		StudentsGroup group1 = new StudentsGroup("321");
		group1.setEmails(asList("a@b.com", "c@d.com"));
		StudentsGroup group2 = new StudentsGroup("322");
		group1.setYear(year);
		year.getGroups().add(group1);
		group2.setYear(year);
		year.getGroups().add(group2);
		
		group1.getLabs().add(lab1);
		lab1.setGroup(group1);
		group2.getLabs().add(lab2);
		lab2.setGroup(group2);
		course.setYear(year);
		
		em.persist(year);
		em.persist(group1);
		em.persist(group2);
		em.persist(course);
		em.persist(lab1);
		em.persist(lab2);
		em.persist(subject);
	}
}
