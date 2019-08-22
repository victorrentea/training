package victor.training.jpa.app.facade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.domain.entity.Subject;

@Service
public class AnotherService {

	private final static Logger log = LoggerFactory.getLogger(AnotherService.class);
	
	@PersistenceContext
	private EntityManager em;
	
	public void checkPermissionsOnSubject(long subjectId) {
		Subject subject = em.find(Subject.class, subjectId);
		log.debug("Checking permissions on instance from database: " + System.identityHashCode(subject));
		
	}

	public void throwException() {
		
		throw new RuntimeException("on purpose");	
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void persistErrorLog(String message) {
		em.persist(new ErrorLog(message));
	}

	
}
