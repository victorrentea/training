package spring.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Transactional;

import spring.test.util.TransactionUtil;

@ContextConfiguration(locations = { "classpath:/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class TransactionUtilTest {
	
	@Autowired
	private TransactionUtil txUtil;

	@Test
	public void testRequiredExecutesInNewTransactionWhenNoneProvided() {
		txUtil.executeWith_REQUIRED(new AssertHasTx());
	}
	
	@Test
	@Transactional
	public void testRequiredExecutesInExistingTransactionIfAvailable() {
		txUtil.executeWith_REQUIRED(new AssertHasTx());
		txUtil.executeWith_REQUIRED(new AssertSameTx(txUtil.getTransactionOpaqueIdentity()));
	}

	@Test
	@Transactional
	public void testRequiresNewExecutesInNewTx() {
		final Object txOpaqueId = txUtil.getTransactionOpaqueIdentity();
		txUtil.executeWith_REQUIRES_NEW(new AssertHasTx());
		txUtil.executeWith_REQUIRES_NEW(new AssertNotSameTx(txOpaqueId));
		assertSame("Running again in Tx1", txOpaqueId, txUtil.getTransactionOpaqueIdentity());
	}
	
	@Test
	@Transactional
	public void testRequiresNewExecutesInNewAliveTxEvenWhenInvokedFromRollbackedTransaction() {
		final Object txOpaqueId = txUtil.getTransactionOpaqueIdentity();
		txUtil.setTransactionRollbackOnly();		
		txUtil.executeWith_REQUIRES_NEW(new AssertHasTx());
		txUtil.executeWith_REQUIRES_NEW(new AssertNotSameTx(txOpaqueId));
		txUtil.executeWith_REQUIRES_NEW(new AssertNotRollbackedTx());
		
		assertSame("Running again in Tx1", txOpaqueId, txUtil.getTransactionOpaqueIdentity());
	}

	@Test
	@Transactional
	public void testNotSupportedExecutesWithoutTxWhenInvokedFromWithinTx() {
		final Object txHandle = txUtil.getTransactionOpaqueIdentity();
		txUtil.executeWith_NOT_SUPPORTED(new AssertDoesnHaveTx());
		assertSame("Running again in Tx1", txHandle, txUtil.getTransactionOpaqueIdentity());
	}

	@Test(expected = IllegalTransactionStateException.class)
	public void testMandatoryFailsWhenInvokedWithoutTx() {
		txUtil.executeWith_MANDATORY(new Runnable() {
			@Override
			public void run() {
				fail("Must never reach this point!");
			}
		});
	}
	
	// Assertions to run in separate Tx
	
	public class AssertHasTx implements Runnable {
		@Override
		public void run() {
			assertTrue("Has Transaction", txUtil.hasTransaction());
		}
	}
	
	public class AssertDoesnHaveTx implements Runnable {
		@Override
		public void run() {
			assertFalse("Executes Without a Transaction", txUtil.hasTransaction());
		}
	}
	
	public class AssertSameTx implements Runnable {
		private Object txOpaqueId;
		
		public AssertSameTx(Object txOpaqueId) {
			this.txOpaqueId = txOpaqueId;
		}

		@Override
		public void run() {
			assertSame("Runing in the same Transaction", 
					txOpaqueId, txUtil.getTransactionOpaqueIdentity());
		}
	}
	
	public class AssertNotSameTx implements Runnable {
		private Object txOpaqueId;
		
		public AssertNotSameTx(Object txOpaqueId) {
			this.txOpaqueId = txOpaqueId;
		}

		@Override
		public void run() {
			assertNotSame("Runing in a different Tx", txOpaqueId, txUtil.getTransactionOpaqueIdentity());
		}
	}
	
	public class AssertNotRollbackedTx implements Runnable {

		@Override
		public void run() {
			assertFalse("Runing in a NON-rollbacked Tx", txUtil.isTransactionRollbacked());
		}
	}


	
	
	
}
