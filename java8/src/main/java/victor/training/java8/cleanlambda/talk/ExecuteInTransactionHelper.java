package victor.training.java8.cleanlambda.talk;

public class ExecuteInTransactionHelper {

	public void execute(Runnable r) {
		startTransaction();
		try {
			r.run();
		} finally {
			closeTransaction();
		}
	}

	private void closeTransaction() {
		// TODO Auto-generated method stub
		
	}

	private void startTransaction() {
		// TODO Auto-generated method stub
		
	}
}
