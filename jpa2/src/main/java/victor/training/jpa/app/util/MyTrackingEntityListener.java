package victor.training.jpa.app.util;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTrackingEntityListener {
	private final static Logger log = LoggerFactory.getLogger(MyTrackingEntityListener.class);
	
	public interface Trackable {
		void setLastModifiedBy(String username);
		void setLastModifiedDate(LocalDateTime dateTime);
	}
	
	@PreUpdate
    @PrePersist
    public void setLastUpdate(Trackable trackable) {
//		log.debug("Updating tracking columns of: {}", trackable);
//		trackable.setLastModifiedDate(LocalDateTime.now());
//		trackable.setLastModifiedBy(MyUtil.getUserOnCurrentThread());
    }
}
