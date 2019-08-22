package victor.training.jpa.app.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class TeacherDetails {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Lob
	private String cv;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCv() {
		return cv;
	}

	public TeacherDetails setCv(String cv) {
		this.cv = cv;
		return this;
	}
	

	
}
