package victor.training.ejb.container.tx.testutil;

import static javax.transaction.Status.STATUS_NO_TRANSACTION;
import static javax.transaction.Status.STATUS_UNKNOWN;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TransactionUtilBean implements TransactionUtil {

	@Resource
	private UserTransaction transaction;

	@Override
	public boolean hasTransaction() {
		try {
			return !Arrays.asList(STATUS_NO_TRANSACTION, STATUS_UNKNOWN).contains(transaction.getStatus());
		} catch (SystemException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getCurrentTransactionStatus() {
		try {
			return transaction.getStatus();
		} catch (SystemException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setTransactionRollbackOnly() {
		try {
			transaction.setRollbackOnly();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isTransactionRollbacked() {
		try {
			return Status.STATUS_MARKED_ROLLBACK == transaction.getStatus();
		} catch (SystemException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public boolean isTransactionValid() {
		try {
			return Status.STATUS_ACTIVE == transaction.getStatus();
		} catch (SystemException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void executeWith_SUPPORTS(Runnable runnable) {
		runnable.run();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void executeWith_REQUIRED(Runnable runnable) {
		runnable.run();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void executeWith_REQUIRES_NEW(Runnable runnable) {
		runnable.run();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void executeWith_NOT_SUPPORTED(Runnable runnable) {
		runnable.run();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void executeWith_MANDATORY(Runnable runnable) {
		runnable.run();
	}
}
