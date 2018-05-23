package my.spring.playground.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course extends BaseEntity {
	private String name;
	private String description;
	private Date startDate;
	private Teacher teacher;

	
	public Course() {
	}
	
	public Course(String name, String description, Date startDate) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
	}


	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final Date getStartDate() {
		return startDate;
	}

	public final void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public final Teacher getTeacher() {
		return teacher;
	}

	public final void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}


}
