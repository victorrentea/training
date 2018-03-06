package ro.victor.training.jpa2.facade.dto;

import java.time.format.DateTimeFormatter;

import ro.victor.training.jpa2.domain.entity.Subject;

public class SubjectDto {

	public Long id;
	public String name;
	public Long holderTeacherId;
	public String lastModifiedByUsername;
	public String lastModifiedDate;
	
	public SubjectDto() {
	}
	
	public SubjectDto(Subject subject) {
		id = subject.getId();
		name = subject.getName();
		if (subject.getHolderTeacher() != null) {
			holderTeacherId = subject.getHolderTeacher().getId();
		}
		lastModifiedByUsername = subject.getLastModifiedBy();
		if (subject.getLastModifiedDate()!= null) {
			lastModifiedDate = subject.getLastModifiedDate().format(DateTimeFormatter.ISO_DATE_TIME);
		}
	}
}
