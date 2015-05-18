package victor.training.mybatis.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Site {
	private Integer id;

	private String name;

	private Company company;

	private List<Employee> employees;

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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
