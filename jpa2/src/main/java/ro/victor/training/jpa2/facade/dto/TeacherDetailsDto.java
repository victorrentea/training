package ro.victor.training.jpa2.facade.dto;

import ro.victor.training.jpa2.domain.entity.Teacher;
import ro.victor.training.jpa2.domain.entity.Teacher.Grade;

public class TeacherDetailsDto {

	public Long id;
	public String name;
	public String cv;
	public Grade grade;
	public TimeSlotDto counselingInterval;
	
	public TeacherDetailsDto() {
	}
	
	public TeacherDetailsDto(Teacher teacher) {
		id = teacher.getId();
		name = teacher.getName();
		grade = teacher.getGrade();
		if (teacher.getDetails() != null) {
			cv = teacher.getDetails().getCv();
		}
		counselingInterval = new TimeSlotDto(
				teacher.getCounselingDay(), 
				teacher.getCounselingStartHour(), 
				teacher.getCounselingDurationInHours(),
				teacher.getCounselingRoomId()
				);
	}
}
