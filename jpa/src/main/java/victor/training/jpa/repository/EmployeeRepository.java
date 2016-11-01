package victor.training.jpa.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import victor.training.jpa.entity.employee.Company;
import victor.training.jpa.entity.employee.Employee;

@Repository
public class EmployeeRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Employee employee) {
		entityManager.persist(employee);
	}

	public Employee getById(Integer employeeId) {
		return entityManager.find(Employee.class, employeeId);
	}
	
	public void persistCompany(Company company) {
		entityManager.persist(company);
		
		for (Employee e:company.getEmployees()) {
			// TODO fill in OWNER link
			e.setCompany(company);
			entityManager.persist(e);
		}
		
	}
	
	public Employee findWithProjects(Integer employeeId) {
		TypedQuery<Employee> query = entityManager.createNamedQuery("getWithProjects", Employee.class);
		query.setParameter("xxy", employeeId);
		return query.getSingleResult();
	}
	
}
