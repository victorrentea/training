package ro.victor.training.jpa2.facade.dto;

import ro.victor.training.jpa2.domain.entity.StudentsGroup;

public class StudentsGroupDto {
	public Long id;
	public String code;

	public StudentsGroupDto() {
	}
	public StudentsGroupDto(StudentsGroup group) {
		id = group.getId();
		code = group.getCode();
	}

}
