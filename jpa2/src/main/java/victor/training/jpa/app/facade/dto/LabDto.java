package victor.training.jpa.app.facade.dto;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

import victor.training.jpa.app.domain.entity.LabActivity;
import victor.training.jpa.app.domain.entity.Teacher;

public class LabDto {

	public Long id;
	public String subjectName;
	public TimeSlotDto timeSlot;
	public String groupCode;
	public Set<String> teacherNames = new TreeSet<>();
	public String lastModifiedByUsername;
	public String lastModifiedDate;
	
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
		lastModifiedByUsername = lab.getLastModifiedBy();
		if (lab.getLastModifiedDate()!= null) {
			lastModifiedDate = lab.getLastModifiedDate().format(DateTimeFormatter.ISO_DATE_TIME);
		}
	}
}
