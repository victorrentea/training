package victor.training.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import victor.training.spring.domain.Notification;

@Repository
public class NotificationDAO {

	@Autowired
	private JdbcTemplate jdbc;

	@Transactional(propagation = Propagation.REQUIRES_NEW) // SOLUTION
	public void persist(Notification notification) {
		jdbc.update("INSERT INTO NOTIFICATION(id,message) values (?,?)", 
				notification.getId(), 
				notification.getMessage());
	}
	
	public long getCount() {
		return jdbc.queryForObject("SELECT COUNT(*) FROM Notification", Integer.class);
	}
	
}
