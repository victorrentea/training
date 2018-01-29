package ro.victor.training.jpa2.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class StudentsGroup {
	@Id
	@GeneratedValue
	private Long id;

	private String code;

	@ManyToOne
	private StudentsYear year;

	@OneToMany(mappedBy = "group")
	private Set<LabActivity> labs = new HashSet<>();

	public StudentsGroup() {
	}

	public StudentsGroup(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public StudentsYear getYear() {
		return year;
	}

	public void setYear(StudentsYear year) {
		this.year = year;
	}

	public Set<LabActivity> getLabs() {
		return labs;
	}

	public void setLabs(Set<LabActivity> labs) {
		this.labs = labs;
	}

}
