package victor.training.jpa;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
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
	public void studentEnrolledThisYear() {
		em.persist(new Student("John")
				.setHistory(new StudentHistory(LocalDate.now().minusYears(1))));
		em.persist(new Student("Kristoph")
				.setHistory(new StudentHistory(LocalDate.now())));
		String jpql = "SELECT s FROM Student s WHERE s.history.enrollmentDate >= :startOfThisYear";
		List<Student> results = em.createQuery(jpql, Student.class)
				.setParameter("startOfThisYear", LocalDate.now().withDayOfYear(1))
				.getResultList();
		assertEquals(1, results.size());
		assertEquals("Kristoph", results.get(0).getName());
	}

	@Test
	public void coursesThaughtByTavi_prefetchingStudent() {
		Student john = new Student("John");
		em.persist(new Course("Sisteme de Operare")
				.addStudent(john)
				.addStudent(new Student("Maria"))
				.setTeacher(new Teacher("Tavi")));
		em.persist(new Course("Limbaje Formale")
				.addStudent(john)
				.addStudent(new Student("Daniel")));

		String jpql = "SELECT c FROM Course c JOIN FETCH c.students WHERE c.teacher.name = 'Tavi'";
		List<Course> results = em.createQuery(jpql, Course.class).getResultList();

		assertEquals(2, results.size());
		assertEquals(2, results.get(0).getStudents().size());
	}

	@Test
	public void howManyCoursesMikeTeachesInAnAula() {
		Teacher tavi = new Teacher("Tavi");
		em.persist(new Course("Sisteme de Operare")
				.setTeacher(tavi)
				.setRoom(new Room(RoomType.AULA)));
		em.persist(new Course("Programarea Sistemelor de Operare")
				.setTeacher(tavi)
				.setRoom(new Room(RoomType.CLASSROOM)));

		String jpql = "SELECT COUNT(c) FROM Course c WHERE c.room.type = 'AULA' and c.teacher.name = 'Tavi'";
		Long count = em.createQuery(jpql, Long.class).getSingleResult();
		assertEquals(new Long(1), count);
	}

	@Test
	public void coursesAttendedByJohnInAnAula() {
		Student john = new Student("John");
		em.persist(new Course("Programarea Calculatoarelor")
			.setRoom(new Room(RoomType.AULA))
			.addStudent(john));
		em.persist(new Course("Ingineria Sistemelor")
			.setRoom(new Room(RoomType.CLASSROOM))
			.addStudent(john));

		String jpql = "SELECT c FROM Course c INNER JOIN c.students s WHERE s.name = 'John' AND c.room.type = 'AULA'";
		List<Course> results = em.createQuery(jpql, Course.class).getResultList();
		assertEquals(1, results.size());
		assertEquals("Programarea Calculatoarelor", results.get(0).getName());

	}

	
	
	@Test
	public void teachersOfLastEnrolledStudent() {
		Student old = new Student("Old")
				.setHistory(new StudentHistory(LocalDate.now().minusYears(1)));
		Student theOne = new Student("The One")
				.setHistory(new StudentHistory(LocalDate.now()));
		Teacher victor = new Teacher("Victor");
		Teacher mariana = new Teacher("Mariana");
		em.persist(new Course("Java").setTeacher(victor).addStudent(theOne));
		em.persist(new Course("PHP").setTeacher(mariana).addStudent(old));
		
		List<Teacher> results = em.createQuery(
				"SELECT c.teacher FROM Student s JOIN s.courses c "
				+ " WHERE s.history.enrollmentDate = (SELECT max(ss.history.enrollmentDate) FROM Student ss) "
				
				, Teacher.class)
			.getResultList();
		
		assertEquals(victor, results.get(0));
		
	}
	
	@Test
	public void enrollmentDate_course_pairs() {
		Student desteptul = new Student("Desteptul");
		Student tantalaul = new Student("Tantalaul");
		em.persist(new Course("Java").addStudent(desteptul).addStudent(tantalaul));
		em.persist(new Course("Filozofie").addStudent(tantalaul));
		
//		String jpql = ""; // INITIAL
		String jpql = "SELECT c.name, s.name FROM Course c JOIN c.students s"; // SOLUTION
		List<Object[]> results = em.createQuery(jpql, Object[].class).getResultList();
		
		assertEquals(3, results.size());
		assertTrue(results.stream().anyMatch(arr -> Arrays.equals(arr, new String[]{"Java", "Desteptul"})));
		
		// TODO: after fixing it, use SELECT new ....
	}
	
	@Test
	public void aulaCoursesNotOfStudentX() {
		Student s = new Student("X");
		em.persist(new Course("Java").addStudent(s).setRoom(new Room(RoomType.AULA)));
		em.persist(new Course("Filozofie").setRoom(new Room(RoomType.AULA)));
		
//		String jpql = ""; // INITIAL
//		String jpql = "SELECT c FROM (SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId) WHERE c.room.type = 'AULA'"; // SOLUTION
//		String jpql = "SELECT c FROM Course c, Student s WHERE s.id = :studentId AND c.room.type = 'AULA' AND c NOT IN s.courses"; // SOLUTION
		String jpql = "SELECT c FROM Course c, Student s JOIN s.courses sc WHERE s.id = :studentId AND c.room.type = 'AULA' AND c NOT IN sc"; // SOLUTION
		List<Course> results = em.createQuery(jpql, Course.class)
				.setParameter("studentId", s.getId())
				.getResultList();
		
		assertEquals(1, results.size());
		assertEquals("Filozofie", results.get(0).getName());
		
		// TODO: after fixing it, use SELECT new ....
	}
	
	@Test
	public void courseWithTheSameNameAsJohnsHeldByADifferentTeacher() {
		
		Student john = new Student("John");
		Teacher myJavaTeacher = new Teacher("My Java Teacher");
		Teacher myAlgoTeacher = new Teacher("My Algo Teacher");
		Teacher unknownTeacher = new Teacher("Unknown Teacher");
		em.persist(new Course("Java").setTeacher(myJavaTeacher).addStudent(john));
		em.persist(new Course("Algo").setTeacher(myAlgoTeacher).addStudent(john));
		Course javaByMyAlgoTeacher = new Course("Java").setTeacher(myAlgoTeacher);
		em.persist(javaByMyAlgoTeacher);
		Course javaByUnknownTeacher = new Course("Java").setTeacher(unknownTeacher);
		em.persist(javaByUnknownTeacher);
		
//		String jpql = ""; // INITIAL
		// SOLUTION(
		String jpql = "SELECT c FROM Course c, Student s JOIN s.courses sc "
				+ " WHERE sc.name = c.name "
				+ " AND c.teacher.id != sc.teacher.id "
				+ " AND s.id = :studentId"; // SOLUTION)
		
		List<Course> otherCourses = em.createQuery(jpql, Course.class).setParameter("studentId", john.getId()).getResultList();
		assertEquals(new HashSet<>(asList(javaByMyAlgoTeacher, javaByUnknownTeacher)), new HashSet<>(otherCourses));

		// SOLUTION(
		jpql = "SELECT t FROM "
				+ " Teacher t JOIN t.courses tc, "
				+ " Student s JOIN s.courses sc "
				+ " WHERE sc.name = tc.name "
				+ " AND s.id = :studentId "
				+ " AND t.id NOT IN (SELECT ssc.teacher.id FROM Student ss JOIN ss.courses ssc where ss.id = :studentId)"; // SOLUTION)

		List<Teacher> otherTeachers = em.createQuery(jpql, Teacher.class).setParameter("studentId", john.getId()).getResultList();
		assertEquals(singletonList(unknownTeacher), otherTeachers);
		
	}
	

}
