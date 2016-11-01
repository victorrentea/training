package victor.training.jpa.entity.teacher;

import java.util.ArrayList;
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
	private List<Course> courses = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "HISTORY_ID")
	private StudentHistory history;

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
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public StudentHistory getHistory() {
		return history;
	}

	public void setHistory(StudentHistory history) {
		this.history = history;
	}

}
