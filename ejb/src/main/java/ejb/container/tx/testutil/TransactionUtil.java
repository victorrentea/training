package ejb.container.tx.testutil;

import javax.ejb.Local;

@Local
public interface TransactionUtil {
	boolean hasTransaction();
	
	boolean isTransactionRollbacked();
	void setTransactionRollbackOnly();
	boolean isTransactionValid();
	
	int getCurrentTransactionStatus();
	
	void executeWith_SUPPORTS(Runnable runnable);
	void executeWith_REQUIRED(Runnable runnable);
	void executeWith_REQUIRES_NEW(Runnable runnable);
	void executeWith_MANDATORY(Runnable runnable);
	void executeWith_NOT_SUPPORTED(Runnable runnable);
}
