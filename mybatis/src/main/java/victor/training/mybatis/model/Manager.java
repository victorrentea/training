package victor.training.mybatis.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Manager extends Employee {
	private List<Project> managedProjects;

	public Manager() {
	}

	public Manager(Integer id) {
		super(id);
	}



	public List<Project> getManagedProjects() {
		return managedProjects;
	}

	public void setManagedProjects(List<Project> managedProjects) {
		this.managedProjects = managedProjects;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
