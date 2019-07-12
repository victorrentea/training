package ro.victor.unittest.db;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ro.victor.unittest.db.prod.NotificationRepo;
import ro.victor.unittest.db.prod.ReportingRepo;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional // SOLUTION
@WithCommonReferenceData // SOLUTION
public class TransactionalTest {
	
	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private ReportingRepo reportingRepo;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Sql// SOLUTION
	@Test
	public void countUsers() {
		int userCount = jdbc.queryForObject("SELECT count(1) FROM users", Integer.class);
		assertEquals(1, userCount);
	}
	
	@Test
	public void testUserIsInDB() {
		assertEquals(1, (int)jdbc.queryForObject("SELECT count(1) FROM users WHERE username='test'", Integer.class));
	}
	
	@Test
	public void notificationTextIsExtractedAfterPersit() {
		notificationRepo.insertNotification("a");
		assertEquals(singletonList("a"), reportingRepo.getAllNotifications());
	}
	
	@Sql("/common-reference-data.sql")// SOLUTION
	@Sql// SOLUTION
	@Test
	public void orderIsFoundByReference() {
		assertEquals(1, (int)jdbc.queryForObject("SELECT count(1) FROM orders WHERE reference='ref'", Integer.class));
	}

}
