package victor.training.jpa.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import victor.training.jpa.entity.teacher.Course;
import victor.training.jpa.entity.teacher.RoomType;
import victor.training.jpa.entity.teacher.Student;

@Repository
public class CourseSearchCriteriaBuilderRepository {
	@PersistenceContext
	protected EntityManager em;
	
	public List<Course> search(CourseSearchCriteria searchCriteria) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> q = cb.createQuery(Course.class);
		Root<Course> r = q.from(Course.class);
		Map<String, Object> params = new HashMap<>();
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.isNotBlank(searchCriteria.namePart)) {
			predicates.add(cb.like(
					cb.upper(r.get("name")), 
					cb.upper(cb.parameter(String.class, "nameParam"))));
			params.put("nameParam", "%" +searchCriteria.namePart + "%");
		}
		
		
		if (searchCriteria.teacherId != null) {
			predicates.add(cb.equal(
					r.get("teacher").get("id"), 
					cb.parameter(Integer.class, "teacherId")));
			params.put("teacherId", searchCriteria.teacherId);
		}

		if (searchCriteria.roomType != null) {
			predicates.add(cb.equal(
					r.get("room").get("type"), 
					cb.parameter(RoomType.class, "roomType")));
			params.put("roomType", searchCriteria.roomType);
		}
				
		
		if (searchCriteria.studentId != null) {
			Subquery<Long> subquery = q.subquery(Long.class);
			Root<Student> student = subquery.from(Student.class); // FROM Student 
			Join<Student, Course> course = student.join("courses"); // JOIN s.courses c
			subquery.select(course.get("id")) // c.id
				.where(cb.equal(student.get("id"), cb.parameter(Integer.class, "studentId")));
			// EXERCISE: Switch courses with student :P
			
			predicates.add(r.get("id").in(subquery));
			params.put("studentId", searchCriteria.studentId);
		}
		
		q.select(r).where(predicates.toArray(new Predicate[0])); 
		
		TypedQuery<Course> query = em.createQuery(q);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		return query.getResultList();
	}
	
}
