package victor.training.jpa.app.facade;

import org.springframework.stereotype.Service;

@Service
public class NonTransactedService {

	public void throwException() {
		throw new RuntimeException("Exception thrown from non-transacted bean (no TransactionInterceptor in front of me), does NOT roll back incoming transaction");
	}
}
