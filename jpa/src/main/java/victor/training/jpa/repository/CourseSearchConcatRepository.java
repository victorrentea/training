package victor.training.jpa.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import victor.training.jpa.entity.teacher.Course;

@Repository
public class CourseSearchConcatRepository {
	@PersistenceContext
	protected EntityManager em;
	
	public List<Course> search(CourseSearchCriteria criteria) {
		String jpql = "SELECT c FROM Course c WHERE 1=1 ";
		Map<String, Object> params = new HashMap<>();
		
		if (StringUtils.isNotBlank(criteria.namePart)) {
			jpql += " AND UPPER(c.name) LIKE UPPER('%' || :name || '%') ";
			params.put("name", criteria.namePart);
		}
		
		if (criteria.teacherId != null) {
			jpql += " AND c.teacher.id = :teacherId";
			params.put("teacherId", criteria.teacherId);
		}

		if (criteria.roomType != null) {
			jpql += " AND c.room.type = :roomType";
			params.put("roomType", criteria.roomType);
		}
		
		if (criteria.studentId != null) {
			jpql += " AND c.id IN (SELECT cc.id FROM Student s JOIN s.courses cc WHERE s.id = :studentId)";
			params.put("studentId", criteria.studentId);
		}
		
		TypedQuery<Course> query = em.createQuery(jpql, Course.class);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		return query.getResultList();
	}
	
}
