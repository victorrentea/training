package ro.victor.training.jpa2.facade.dto;

import ro.victor.training.jpa2.domain.entity.Teacher;
import ro.victor.training.jpa2.domain.entity.Teacher.Grade;

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
