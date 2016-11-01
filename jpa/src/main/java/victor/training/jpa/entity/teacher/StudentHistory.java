package victor.training.jpa.entity.teacher;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "STUDENT_HISTORY")
public class StudentHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date enrollmentDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

}
