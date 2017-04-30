package victor.training.jpa.facade.dto;

import java.util.ArrayList;
import java.util.List;

import victor.training.jpa.entity.ProjectType;

public class ProjectDto {

	public String name;
	public ProjectType type;
	public Integer managerId;
	public Integer id;
	public List<Integer> employeeIds = new ArrayList<>();;

}
