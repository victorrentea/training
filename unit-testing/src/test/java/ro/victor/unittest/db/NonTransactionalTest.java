package ro.victor.unittest.db;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import ro.victor.unittest.db.prod.NotificationRepo;
import ro.victor.unittest.db.prod.ReportingRepo;

@SpringBootTest
@ActiveProfiles("realdb")
@RunWith(SpringRunner.class)
// SOLUTION (
@WithCommonReferenceData
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, 
	statements = {
		"DELETE FROM ORDERS",
		"DELETE FROM USERS"
})
// SOLUTION )
public class NonTransactionalTest {
	
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
//	@CleanupSql// SOLUTION
	@Test
	public void orderIsFoundByReference() {
		assertEquals(1, (int)jdbc.queryForObject("SELECT count(1) FROM orders WHERE reference='ref'", Integer.class));
	}

//	public static void main(String[] args) {
//		System.out.println("Order of tests: ");
//		Stream.of(TransactionalTest.class.getDeclaredMethods())
//			.map(Method::getName)
//			.sorted(comparing(String::hashCode))
//			.forEach(System.out::println);
//	}
	
}
