package victor.training.jpa.app.facade.dto;

import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;

public class TeacherDto {

	public Long id;
	public String name;
	public Grade grade;
	
	public TeacherDto() {
	}
	
	public TeacherDto(Teacher teacher) {
		id = teacher.getId();
		name = teacher.getName();
		grade = teacher.getGrade();
	}
}
