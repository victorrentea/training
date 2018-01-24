package victor.training.jpa.entity.teacher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;

	@ManyToMany
	@JoinTable(name = "COURSE_STUDENT", joinColumns = @JoinColumn(name = "STUDENT_ID"), inverseJoinColumns = @JoinColumn(name = "COURSE_ID")) 
	List<Course> courses = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "HISTORY_ID")
	private StudentHistory history = new StudentHistory(LocalDate.now().withDayOfYear(1));

	public Student(String name) {
		this.name = name;
	}
	
	public Student() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Course> getCourses() {
		return Collections.unmodifiableList(courses);
	}


	public void setCourses(List<Course> newCourses) {
		List<Course> oldCourses = new ArrayList<>(this.courses);
		oldCourses.forEach(this::removeCourse); 
		newCourses.forEach(this::addCourse);
	}
	
	public void addCourse(Course course) {
		this.courses.add(course);
		course.students.add(this);
	}
	
	public void removeCourse(Course course) {
		this.courses.remove(course);
		course.students.remove(this);
	}

	public StudentHistory getHistory() {
		return history;
	}

	public Student setHistory(StudentHistory history) {
		this.history = history;
		return this;
	}

}
