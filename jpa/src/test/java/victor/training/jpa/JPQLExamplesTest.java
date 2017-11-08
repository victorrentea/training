package victor.training.jpa;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.entity.teacher.Course;
import victor.training.jpa.entity.teacher.Room;
import victor.training.jpa.entity.teacher.RoomType;
import victor.training.jpa.entity.teacher.Student;
import victor.training.jpa.entity.teacher.StudentHistory;
import victor.training.jpa.entity.teacher.Teacher;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles("hsql")
public class JPQLExamplesTest {
	@PersistenceContext
	protected EntityManager em;

	@Test
	@Rollback
	public void testExample1() {
		prepareDataForQ1();

		String jpql = "SELECT s FROM Student s WHERE s.history.enrollmentDate >= '2013-01-01'";
		List<Student> results = em.createQuery(jpql, Student.class).getResultList();
		assertEquals(1, results.size());
		assertEquals("John", results.get(0).getName());
	}

	@Test
	@Rollback
	public void testExample2() {
		prepareDataForQ2();

		String jpql = "SELECT c FROM Course c JOIN FETCH c.students WHERE c.teacher.name = 'Mike'";
		List<Course> results = em.createQuery(jpql, Course.class).getResultList();

		assertEquals(2, results.size());
		assertEquals(2, results.get(0).getStudents().size());
	}

	@Test
	@Rollback
	public void testQuizQuestion3() {
		prepareDataForQ3();

		String jpql = "SELECT COUNT(c) FROM Course c WHERE c.room.type = 'AULA' and c.teacher.name = 'Mike'";
		Long count = em.createQuery(jpql, Long.class).getSingleResult();
		assertEquals(new Long(1), count);
	}

	@Test
	@Rollback
	public void testQuizQuestion4() {
		prepareDataForQ4();

		String jpql = "SELECT c FROM Course c INNER JOIN c.students s WHERE s.name = 'John' AND c.room.type = 'AULA'";
		List<Course> results = em.createQuery(jpql, Course.class).getResultList();
		assertEquals(1, results.size());
		assertEquals("Programarea Calculatoarelor", results.get(0).getName());

	}

	// / DATA set-up

	private void prepareDataForQ1() {
		try {
			Student john = new Student("John");
			john.setHistory(new StudentHistory());
			john.getHistory().setEnrollmentDate(new SimpleDateFormat("yyyy-MM-dd").parse("2013-04-19"));

			Student Kristoph = new Student("Kristoph");
			Kristoph.setHistory(new StudentHistory());
			Kristoph.getHistory().setEnrollmentDate(new SimpleDateFormat("yyyy-MM-dd").parse("2012-10-10"));

			em.persist(john);
			em.persist(Kristoph);
			em.flush();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void prepareDataForQ2() {
		Student john = new Student("John"), maria = new Student("Maria"), daniel = new Student("Daniel");

		Course course1 = new Course("Ingineria Programarii");
		Course course2 = new Course("Limbaje Formale");

		john.getCourses().add(course1);
		course1.getStudents().add(john);

		john.getCourses().add(course2);
		course2.getStudents().add(john);

		maria.getCourses().add(course1);
		course1.getStudents().add(maria);

		daniel.getCourses().add(course2);
		course2.getStudents().add(daniel);

		Teacher profMike = new Teacher("Mike");
		course1.setTeacher(profMike);

		em.persist(profMike);
		em.persist(course1);
		em.persist(course2);
		em.persist(john);
		em.persist(maria);
		em.persist(daniel);
		em.flush();

	}

	private void prepareDataForQ3() {
		Teacher mike = new Teacher("Mike");
		Course course1 = new Course("Programarea Calculatoarelor");
		Course course2 = new Course("Ingineria Sistemelor");
		course1.setTeacher(mike);
		course2.setTeacher(mike);
		Room room1 = new Room(RoomType.AULA);
		Room room2 = new Room(RoomType.CLASSROOM);
		course1.setRoom(room1);
		course2.setRoom(room2);

		em.persist(room1);
		em.persist(room2);
		em.persist(course1);
		em.persist(course2);
		em.persist(mike);
		em.flush();
	}

	private void prepareDataForQ4() {
		Student john = new Student("John");
		Course course1 = new Course("Programarea Calculatoarelor");
		Course course2 = new Course("Ingineria Sistemelor");
		Room room1 = new Room(RoomType.AULA);
		Room room2 = new Room(RoomType.CLASSROOM);
		course1.setRoom(room1);
		course2.setRoom(room2);
		john.setCourses(Arrays.asList(course1, course2));
		em.persist(room1);
		em.persist(room2);
		em.persist(course1);
		em.persist(course2);
		em.persist(john);
		em.flush();
	}

}
