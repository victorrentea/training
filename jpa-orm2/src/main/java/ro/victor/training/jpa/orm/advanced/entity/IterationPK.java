package ro.victor.training.jpa.orm.advanced.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class IterationPK implements Serializable {
	private long project;
	private int iteration;
	public IterationPK() {
	}
	
	
	public IterationPK(long project, int iteration) {
		this.project = project;
		this.iteration = iteration;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + iteration;
		result = prime * result + (int) (project ^ (project >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IterationPK other = (IterationPK) obj;
		if (iteration != other.iteration)
			return false;
		if (project != other.project)
			return false;
		return true;
	}
	
}