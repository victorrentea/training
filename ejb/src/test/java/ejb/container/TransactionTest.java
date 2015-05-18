package ejb.container;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.jboss.arquillian.junit.Arquillian;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import ejb.EJBTest;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionTest extends EJBTest {

	@Test
	public void testHasTransactionWhenNoTransaction() {
		assertFalse(txUtil.hasTransaction());
	}

	@Test
	public void testHasTransactionWhenInTransaction() {
		txUtil.executeWith_REQUIRED(new Runnable() {
			@Override
			public void run() {
				assertTrue(txUtil.hasTransaction());
			}
		});
	}

	@Test
	public void testREQUIRES_NEW_runsWithAliveTxDespiteOfRollbackedCallerTransaction() {
		txUtil.executeWith_REQUIRED(new Runnable() {
			@Override
			public void run() {
				txUtil.setTransactionRollbackOnly();
				assertTrue(txUtil.isTransactionRollbacked());
				txUtil.executeWith_REQUIRES_NEW(new Runnable() {
					@Override
					public void run() {
						assertFalse(txUtil.isTransactionRollbacked());
					}
				});
				assertTrue(txUtil.isTransactionRollbacked());
			}
		});
	}
	
	@Test
	public void testREQUIRES_NEW_doesntRollbackCallerTx() {
		txUtil.executeWith_REQUIRED(new Runnable() {
			@Override
			public void run() {
				assertFalse(txUtil.isTransactionRollbacked());
				txUtil.executeWith_REQUIRES_NEW(new Runnable() {
					@Override
					public void run() {
						txUtil.setTransactionRollbackOnly();
						assertTrue(txUtil.isTransactionRollbacked());
					}
				});
				assertFalse(txUtil.isTransactionRollbacked());
			}
		});
	}

}
