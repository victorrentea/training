package spring.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import spring.model.Employee;

public class EmployeeDAOJpa implements EmployeeDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void persist(Employee employee) {
		em.persist(employee);
	}

	@Override
	public Employee getById(String id) {
		List<Employee> list = em.createQuery("SELECT e from Employee e where e.id=:id", Employee.class)
				.setParameter("id", id)
				.getResultList();
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public void update(Employee employee) {
		// Nothing to do here: JPA automatically merges changes on Tx commit 
	}

	@Override
	public void removeById(String employeeId) {
		em.createQuery("DELETE FROM Employee e WHERE e.id = :id")
			.setParameter("id", employeeId)
			.executeUpdate();
	}

}
