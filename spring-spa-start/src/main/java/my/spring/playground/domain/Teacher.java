package my.spring.playground.domain;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends BaseEntity {
	private String name;
	private List<Course> courses = new ArrayList<Course>();

	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final List<Course> getCourses() {
		return courses;
	}

	public final void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}
