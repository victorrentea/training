package victor.training.jpa.app.domain.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
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
	
	@ElementCollection
	private List<String> emails = new ArrayList<>();

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

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	
}
