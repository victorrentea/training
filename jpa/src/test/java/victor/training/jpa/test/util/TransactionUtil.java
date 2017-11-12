package victor.training.jpa.test.util;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class TransactionUtil {

	private final TransactionTemplate transactionTemplate;
	
	@Autowired
	public TransactionUtil(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public boolean hasTransaction() {
		return ((DefaultTransactionStatus) getCurrentTransactionStatus()).hasTransaction();
	}

	private TransactionStatus getCurrentTransactionStatus() {
		TransactionStatus actualDefinition = transactionTemplate.getTransactionManager().getTransaction(
				new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_SUPPORTS));
		return actualDefinition;
	}

	public Object getTransactionOpaqueIdentity() {
		JdbcTransactionObjectSupport txImplementation = (JdbcTransactionObjectSupport) ((DefaultTransactionStatus) getCurrentTransactionStatus())
				.getTransaction();
		return txImplementation.getConnectionHolder().getConnectionHandle();
	}

	public void setTransactionRollbackOnly() {
		if (!hasTransaction()) {
			throw new IllegalStateException("Currently, there is no active Tx.");
		}
		transactionTemplate.getTransactionManager().rollback(getCurrentTransactionStatus());
	}
	
	public boolean isTransactionRollbacked() {
		if (!hasTransaction()) {
			throw new IllegalStateException("Currently, there is no active Tx.");
		}
		return getCurrentTransactionStatus().isRollbackOnly();
	}

	public boolean isTransactionNew() {
		if (!hasTransaction()) {
			throw new IllegalStateException("Currently, there is no active Tx.");
		}
		return getCurrentTransactionStatus().isNewTransaction();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void executeWith_SUPPORTS(Runnable runnable) {
		runnable.run();
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	public void executeWith_REQUIRED(Runnable runnable) {
		runnable.run();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void executeInSeparateTransaction(Runnable runnable) {
		runnable.run();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> T executeInSeparateTransaction(Callable<T> runnable) throws Exception {
		return runnable.call();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void executeWith_NOT_SUPPORTED(Runnable runnable) {
		runnable.run();
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public void executeWith_MANDATORY(Runnable runnable) {
		runnable.run();
	}
}
