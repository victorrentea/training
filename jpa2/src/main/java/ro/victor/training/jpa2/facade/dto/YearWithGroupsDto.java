package ro.victor.training.jpa2.facade.dto;

import java.util.ArrayList;
import java.util.List;

import ro.victor.training.jpa2.domain.entity.StudentsGroup;
import ro.victor.training.jpa2.domain.entity.StudentsYear;

public class YearWithGroupsDto {

	public Long id;
	public String code;
	public List<StudentsGroupDto> groups = new ArrayList<>();
	
	public YearWithGroupsDto() {
	}
	public YearWithGroupsDto(StudentsYear year) {
		id = year.getId();
		code = year.getCode();
		for (StudentsGroup group : year.getGroups()) {
			groups.add(new StudentsGroupDto(group));
		}
	}

}
