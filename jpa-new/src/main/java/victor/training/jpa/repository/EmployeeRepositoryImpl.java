package victor.training.jpa.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import victor.training.jpa.entity.Employee;
import victor.training.jpa.entity.search.EmployeeSearchCriteria;

public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Employee> search(EmployeeSearchCriteria criteria) {
		String jpql = "SELECT e FROM Employee e WHERE 1=1 ";
		Map<String, Object> params = new HashMap<>();
		if (criteria.name != null) {
			jpql += " AND UPPER(e.name) LIKE '%' + UPPER(:name) | '%' ";
			params.put("name", criteria.name);
		}
		if (criteria.siteId != null) {
			jpql += " AND e.site.id = :siteId ";
			params.put("siteId", criteria.siteId);
		}
		TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		return query.getResultList();
	}
	
}
