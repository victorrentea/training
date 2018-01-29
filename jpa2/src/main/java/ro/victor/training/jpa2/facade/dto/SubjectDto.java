package ro.victor.training.jpa2.facade.dto;

import ro.victor.training.jpa2.domain.entity.Subject;

public class SubjectDto {

	public Long id;
	public String name;
	public Long holderTeacherId;
	
	public SubjectDto() {
	}
	
	public SubjectDto(Subject subject) {
		id = subject.getId();
		name = subject.getName();
		if (subject.getHolderTeacher() != null) {
			holderTeacherId = subject.getHolderTeacher().getId();
		}
	}
}
