package ro.victor.training.jpa.orm.advanced.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(IterationPK.class)
public class Iteration {

	@Id
	private Integer iteration;
	
	@Id
	@ManyToOne
	private Project project;

	public Integer getIteration() {
		return iteration;
	}

	public void setIteration(Integer iteration) {
		this.iteration = iteration;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}


}

