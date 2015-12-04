package spring.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import spring.model.Notification;
import spring.test.util.TransactionUtil;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
//@Ignore
// Don't do such a test in production applications. Only for DEMO and Tx Understaning
public class NotificationDAOTest {

	@Autowired
	private TransactionUtil txUtil;

	@Autowired
	private NotificationDAO notificationDao;

	@Before
	public void injectMockEntityManager() throws IllegalArgumentException, IllegalAccessException {
		EntityManager mockEntityManager = Mockito.mock(EntityManager.class);
		Mockito.doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				assertTrue(".persist() executes within a Transaction", txUtil.hasTransaction());
				assertFalse(".persist() executes in NON-rollbacked Transaction", txUtil.isTransactionRollbacked());
				return null;
			}

		}).when(mockEntityManager).persist(Mockito.anyString());
		;
		notificationDao.setEm(mockEntityManager);

	}

	@Test
	@Transactional
	public void testPersistRunsInLiveTxWhenInvokedWithTx() {
		notificationDao.persist(new Notification());
	}

	@Test
	public void testPersistRunsInLiveTxWhenInvokedWithoutTx() {
		notificationDao.persist(new Notification());
	}

	@Test
	@Transactional
	public void testPersistRunsInLiveTxWhenInvokedWithRollbackedTx() {
		txUtil.setTransactionRollbackOnly();
		assertTrue("Caller Tx is rollbacked", txUtil.isTransactionRollbacked());
		notificationDao.persist(new Notification());
	}

}
