package victor.training.jpa.entity.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="NAME", unique=true)
	private String name;
	
	@Embedded
	private Address address = new Address();

	@OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
	private List<Employee> employees = new ArrayList<>();
	
//	private String domainAreas; // INITIAL
	@ManyToMany
	@JoinTable
//	@OrderColumn(name="INDEX") // SOLUTION
	private Set<DomainArea> domainAreas;

	public Company() {
	}
	
	public Company(String name) {
		this.name = name;
	}
	
	// INITIAL (
//	public String getDomainAreas() {
//		return domainAreas;
//	}
//	public void setDomainAreas(String domainAreas) {
//		this.domainAreas = domainAreas;
//	}
	// INITIAL )
	
	// SOLUTION (
	public Set<DomainArea> getDomainAreas() {
		return domainAreas;
	}
	public void setDomainAreas(Set<DomainArea> domainAreas) {
		this.domainAreas = domainAreas;
	}
	// SOLUTION )
	

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
}
