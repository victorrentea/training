package victor.training.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import victor.training.jpa.model.employee.Company;
import victor.training.jpa.model.employee.Employee;

public class EmployeeDAOJpa implements EmployeeDAO{

	@PersistenceContext
	private EntityManager em;

	@Override
	public void persist(Employee employee) {
		em.persist(employee);
	}

	@Override
	public Employee getById(Integer employeeId) {
		return em.find(Employee.class, employeeId);
	}
	
	@Override
	public void persistCompany(Company company) {
		em.persist(company);
		
		for (Employee e:company.getEmployees()) {
			// TODO fill in OWNER link
			e.setCompany(company);
			em.persist(e);
		}
		
	}
	
	@Override
	public Employee findWithProjects(Integer employeeId) {
		TypedQuery<Employee> query = em.createNamedQuery("getWithProjects", Employee.class);
		query.setParameter("xxy", employeeId);
		return query.getSingleResult();
	}
	
}
