package victor.training.jpa.repository.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import victor.training.jpa.entity.employee.Employee;

public class EmployeeDataRepositoryImpl implements EmployeeDataRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
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
