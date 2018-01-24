package victor.training.jpa.entity.employee;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EMPLOYEE_DETAILS")
public class EmployeeDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name = "EMPLOYEMENT_DATE")
	private Date startDate;
	
//	@Lob
//	private LobHelper
	// TODO continue;

	@OneToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private Employee employee;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}
	// TODO an Optional<Date> getter
	// SOLUTION (
	public Optional<Date> getStartDateOpt() {
		return Optional.ofNullable(startDate);
	}
	// SOLUTION )

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
