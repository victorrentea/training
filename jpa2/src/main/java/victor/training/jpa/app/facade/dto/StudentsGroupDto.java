package victor.training.jpa.app.facade.dto;

import java.util.ArrayList;
import java.util.List;

import victor.training.jpa.app.domain.entity.StudentsGroup;

public class StudentsGroupDto {
	public Long id;
	public String code;
	public List<String> emails = new ArrayList<>();

	public StudentsGroupDto() {
	}
	public StudentsGroupDto(StudentsGroup group) {
		id = group.getId();
		code = group.getCode();
		emails = group.getEmails();
	}
	public Long getId() {
		return id;
	}

}
