package victor.training.jee6.ejb;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

import victor.training.jee6.validation.Address;

@Stateless
public class AsyncEJB {

	@Asynchronous
	public void fireAndForgetMethod() {
		waitSomeTime();
		System.out.println("fireAndForget stuff");
	}

	@Asynchronous
	public Future<Integer> asyncMethodWithResult() {
		waitSomeTime();
		int response = (int) (Math.random() * 100);
		return new AsyncResult<Integer>(response);
	}

	@Asynchronous
	public Future<Integer> byReferenceParameters(Address address) {
		address.setStreet("anotherStreet");
		return new AsyncResult<Integer>(0);
	}

	private void waitSomeTime() {
		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
