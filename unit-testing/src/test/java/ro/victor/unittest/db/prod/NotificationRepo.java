package ro.victor.unittest.db.prod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepo {
	private final static Logger log = LoggerFactory.getLogger(NotificationRepo.class);
	
	@Autowired
	private JdbcTemplate jdbc;
	
	public void insertNotification(String text) {
		log.debug("Inserting notification on DB connection: " + DataSourceUtils.getConnection(jdbc.getDataSource()));
		jdbc.update("INSERT INTO NOTIFICATIONS(text) VALUES (?)", text);
	}
}
