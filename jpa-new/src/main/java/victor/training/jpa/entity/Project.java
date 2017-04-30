package victor.training.jpa.entity;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * @author VictorRentea
 * 
 */
@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;

	@Enumerated
	private ProjectType type = ProjectType.PUBLIC;

	@ManyToOne
	@JoinColumn(name = "MANAGER_ID")
	private Manager manager;

	@ManyToMany
	@JoinTable(name = "EMP_PRJ", joinColumns = @JoinColumn(name = "PROJECT_ID"), inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
	private List<Employee> employees = new ArrayList<>();
	
	public Project() {
	}
	

	public Project(String name) {
		this.name = name;
	}



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

	public ProjectType getType() {
		return type;
	}

	public void setType(ProjectType type) {
		this.type = type;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

// INITIAL(	
//	public List<Employee> getEmployees() {
//		return employees;
//	}
//
//	public void setEmployees(List<Employee> employees) {
//		this.employees = employees;
//	}
// INITIAL)
	
	// SOLUTION(
	public List<Employee> getEmployees() {
		return unmodifiableList(employees);
	} 
	
	public void addEmployee(Employee employee) {
		employees.add(employee);
		employee._addProject(this);
	}
	
	public void removeEmployee(Employee employee) {
		employees.remove(employee);
		employee._removeProject(this);
	}
	// SOLUTION)
	
}
