package victor.training.jpa.entity.employee;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends Employee {
	@OneToMany(mappedBy = "manager")
	private List<Project> managedProjects;

	public Manager() {
	}

	public Manager(String name) {
		super(name);
	}

	public List<Project> getManagedProjects() {
		return managedProjects;
	}

	public void setManagedProjects(List<Project> managedProjects) {
		this.managedProjects = managedProjects;
	}

}
