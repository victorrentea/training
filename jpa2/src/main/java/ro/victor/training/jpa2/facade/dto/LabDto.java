package ro.victor.training.jpa2.facade.dto;

import java.util.Set;
import java.util.TreeSet;

import ro.victor.training.jpa2.domain.entity.LabActivity;
import ro.victor.training.jpa2.domain.entity.Teacher;

public class LabDto {

	public Long id;
	public String subjectName;
	public TimeSlotDto timeSlot;
	public String groupCode;
	public Set<String> teacherNames = new TreeSet<>();
	
	public LabDto() {
	}
	
	public LabDto(LabActivity lab) {
		id = lab.getId();
		subjectName = lab.getSubject().getName();
		timeSlot =  new TimeSlotDto(lab.getDay(), lab.getDurationInHours(), lab.getDurationInHours(), lab.getRoomId());
		if (lab.getGroup() != null) {
			groupCode = lab.getGroup().getCode();
		}
		for (Teacher teacher : lab.getTeachers()) {
			teacherNames.add(teacher.getName());
		}
	}
}
