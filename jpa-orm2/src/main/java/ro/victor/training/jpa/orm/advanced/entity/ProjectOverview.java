package ro.victor.training.jpa.orm.advanced.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_overview")
public class ProjectOverview {
	@Id
	@Column(name = "PROJECT_NAME")
	private String projectName;

	@Column(name = "ITERATION_COUNT")
	private String iterationCount;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getIterationCount() {
		return iterationCount;
	}

	public void setIterationCount(String iterationCount) {
		this.iterationCount = iterationCount;
	}

	@Override
	public String toString() {
		return "ProjectOverview [projectName=" + projectName + ", iterationCount=" + iterationCount + "]";
	}

	
}
