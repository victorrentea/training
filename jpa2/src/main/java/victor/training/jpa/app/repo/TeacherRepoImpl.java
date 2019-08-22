package victor.training.jpa.app.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.TeachingActivity;
import victor.training.jpa.app.facade.dto.ActivitySearchCriteria;

public class TeacherRepoImpl implements TeacherRepoCustom{
	
	@PersistenceContext
	private EntityManager em;
	
	
	
	
	// TODO do a mistake in JPQL
	public List<Teacher> getAllTeachersForYear(long yearId) {
		TypedQuery<Teacher> query = em.createQuery("SELECT t FROM TeachingActivity a JOIN a.teachers t WHERE "
				+ "a.id IN (SELECT c.id FROM StudentsYear y JOIN y.courses c WHERE y.id = :yearId) "
				+ "OR a.id IN (SELECT lab.id FROM StudentsYear y JOIN y.groups g JOIN g.labs lab WHERE y.id = :yearId)", 
				Teacher.class);
		query.setParameter("yearId", yearId);
		return query.getResultList();
	}


	@Override
	public List<TeachingActivity> searchActivity(ActivitySearchCriteria criteria) {
		Map<String, Object> params = new HashMap<>();
		String jpql = "SELECT a FROM TeachingActivity a WHERE 1=1 ";
		
		if (StringUtils.isNotBlank(criteria.subject)) {
			jpql += " AND UPPER(a.subject.name) LIKE UPPER('%' || :subject || '%') ";
			params.put("subject", criteria.subject);
		}
		
		if (StringUtils.isNotBlank(criteria.roomId)) {
			jpql += " AND UPPER(a.roomId) = :roomId";
			params.put("roomId", criteria.roomId);
		}

		if (criteria.day != null) {
			jpql += " AND a.day = :day";
			params.put("day", criteria.day);
		}
		
		TypedQuery<TeachingActivity> query = em.createQuery(jpql, TeachingActivity.class);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		return query.getResultList();
	}

}
