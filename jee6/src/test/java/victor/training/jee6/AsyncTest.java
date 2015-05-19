package victor.training.jee6;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Future;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.jee6.ejb.AsyncEJB;
import victor.training.jee6.validation.Address;

@RunWith(Arquillian.class)
public class AsyncTest extends AbstractArquillianTest {
	@Inject
	private AsyncEJB asyncEJB;

	@Test
	public void callFireAndForget() throws Exception {
		asyncEJB.fireAndForgetMethod();
		System.out.println("Control returned to caller thread. Thread can terminate");
		Thread.sleep(3 * 1000); // wait for the JUnit to terminate gracefully
	}

	@Test
	public void callAsyncAndWaitForResult() throws Exception {
		Future<Integer> futureResult = asyncEJB.asyncMethodWithResult();
		System.out.println("Control returned to caller thread. Block for results");
		
		Integer result = futureResult.get();
		System.out.println("Actual result received: " + result);
	}

	@Test
	public void parametersPassedByReferenceInAsyncCall() throws Exception {
		Address address = new Address();
		address.setStreet("initialStreet");
		Future<Integer> futureResult = asyncEJB.byReferenceParameters(address);
		futureResult.get();
		assertEquals("anotherStreet", address.getStreet());
	}

}
