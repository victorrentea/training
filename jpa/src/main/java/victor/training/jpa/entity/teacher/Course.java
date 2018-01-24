package victor.training.jpa.entity.teacher;

import static java.util.Collections.unmodifiableList;
import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@ManyToOne(cascade = ALL)
	@JoinColumn(name = "TEACHER_ID")
	private Teacher teacher;

	@ManyToOne(cascade = ALL)
	@JoinColumn(name = "ROOM_ID")
	private Room room;

	@ManyToMany(mappedBy = "courses", cascade = ALL) 
	List<Student> students = new ArrayList<>();

	public Course() {
	}

	public Course(String name) {
		this.name = name;
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

	public Teacher getTeacher() {
		return teacher;
	}

	public Course setTeacher(Teacher teacher) {
		this.teacher = teacher;
		teacher.getCourses().add(this);
		return this;
	}

	public Room getRoom() {
		return room;
	}

	public Course setRoom(Room room) {
		this.room = room;
		return this;
	}

	public List<Student> getStudents() {
		return unmodifiableList(students);
	}

	public void setStudents(List<Student> newStudents) {
		List<Student> oldStudents = new ArrayList<>(this.students);
		oldStudents.forEach(this::removeStudent); 
		newStudents.forEach(this::addStudent);
	}
	
	public Course addStudent(Student student) {
		this.students.add(student);
		student.courses.add(this);
		return this;
	}
	
	public void removeStudent(Student student) {
		this.students.remove(student);
		student.courses.remove(this);
	}

}
