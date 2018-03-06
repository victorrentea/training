package ro.victor.training.jpa.orm.advanced.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
//@NamedStoredProcedureQuery(name = "Task.plus1", 
//			procedureName = "plus1inout", parameters = {
//		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "arg", type = Integer.class),
//		  @StoredProcedureParameter(mode = ParameterMode.OUT, name = "res", type = Integer.class) })
public class Task {
	@Id
	private Long id;
	
	@ManyToOne
	private Iteration iteration;
	
}
