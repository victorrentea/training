package victor.training.jpa.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import victor.training.jpa.entity.Employee;

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
	
	public Long countByName(String name) {
		TypedQuery<Long> query = entityManager.createNamedQuery("Employee_countByName", Long.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}
	
	public List<Employee> getAllFetchProjects() {
		String jpql = "SELECT e FROM Employee e LEFT JOIN FETCH e.projects"; // SOLUTION
		//String jpql = "SELECT e FROM Employee e"; // TODO FETCH here // INITIAL
		TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
		return query.getResultList();
	}
	
	public List<Employee> search(String name, String siteName) {
		Map<String, Object> paramMap = new HashMap<>();
		String jpql = "SELECT e FROM Employee e WHERE 1=1 ";
		
		if (name != null) {
			jpql+= " AND e.name = :name ";
			paramMap.put("name", name);
		}
		if (siteName != null) {
			jpql+= " AND e.site.name = :siteName ";
			paramMap.put("siteName", siteName);
		}
		
		TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
		for (String key : paramMap.keySet()) {
			query.setParameter(key, paramMap.get(key));
		}
		return query.getResultList();
	}
	
}
