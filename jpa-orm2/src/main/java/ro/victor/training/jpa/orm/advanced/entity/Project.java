package ro.victor.training.jpa.orm.advanced.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Project {
	@Id
	@GeneratedValue
	private Long id;

	@Column
	@NotNull
	private String name;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="en", column=@Column(name="DESC_EN")),
		@AttributeOverride(name="fr", column=@Column(name="DESC_FR"))
	})
	private LabelVO description;
	
	@OneToMany(mappedBy="project")
	private List<Iteration> iterations = new ArrayList<>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Iteration> getIterations() {
		return iterations;
	}


	public void setIterations(List<Iteration> iterations) {
		this.iterations = iterations;
	}

}
