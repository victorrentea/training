package victor.training.jpa;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.entity.teacher.Course;
import victor.training.jpa.entity.teacher.Room;
import victor.training.jpa.entity.teacher.RoomType;
import victor.training.jpa.entity.teacher.Student;
import victor.training.jpa.entity.teacher.Teacher;
import victor.training.jpa.repository.CourseSearchCriteria;
import victor.training.jpa.repository.CourseSearchMetamodelRepository;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("hsql")
public class JQPLSearchTest {
	
	@Autowired
//	private CourseSearchConcatRepository repo;
//	private CourseSearchCriteriaBuilderRepository repo;
	private CourseSearchMetamodelRepository repo;
	
	@PersistenceContext
	private EntityManager em;
	
	private CourseSearchCriteria criteria = new CourseSearchCriteria();
	
	@Test
	public void searchByName() {
		Course c = persist(new Course().setName("aBc"));
		persist(new Course().setName("y"));
		
		criteria.namePart = "x";
		assertEquals(emptyList(), repo.search(criteria));
		
		criteria.namePart = "b";
		assertEquals(asList(c), repo.search(criteria));
	}
	
	@Test
	public void searchByTeacherId() {
		Teacher t = new Teacher();
		Course c = persist(new Course().setTeacher(t));
		persist(new Course().setTeacher(new Teacher()));
		
		criteria.teacherId = -3;
		assertEquals(emptyList(), repo.search(criteria));
		
		criteria.teacherId = t.getId();
		assertEquals(asList(c), repo.search(criteria));
	}
	
	@Test
	public void searchByRoomType() {
		Course c = persist(new Course().setRoom(new Room(RoomType.AULA)));
		persist(new Course().setRoom(new Room(RoomType.CLASSROOM)));
		
		criteria.roomType = RoomType.LIBRARY;
		assertEquals(emptyList(), repo.search(criteria));
		
		criteria.roomType = RoomType.AULA;
		assertEquals(asList(c), repo.search(criteria));
	}
	
	@Test
	public void searchByStudentId() {
		Student s = new Student();
		Course c1 = persist(new Course().addStudent(s));
		Course c2 = persist(new Course().addStudent(s));
		persist(new Course());
		
		criteria.studentId = -3;
		assertEquals(emptyList(), repo.search(criteria));
		
		criteria.studentId= s.getId();
		assertEquals(asList(c1, c2), repo.search(criteria));
	}
	
	private <E> E persist(E e) {
		em.persist(e);
		return e;		
	}
	
}
