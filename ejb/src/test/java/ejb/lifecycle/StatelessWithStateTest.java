package ejb.lifecycle;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import java.util.concurrent.Callable;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import ejb.EJBTest;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatelessWithStateTest extends EJBTest {

	@EJB
	private StatelessWithState stateless;

	@Test
	public void test1Once() throws InterruptedException {
		String value = UUID.randomUUID().toString();
		stateless.setBusinessParameter(value);

		String result = stateless.executeBusiness();
		assertEquals(value, result);
	}

	@Test
	public void test2SerializedAccess() throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			test1Once();
		}
	}

	@Test
	public void test3ParallelAccess() throws Throwable {
		executeInParallel(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				String value = UUID.randomUUID().toString();
				stateless.setBusinessParameter(value);

				Thread.sleep((long) (100 * Math.random()));
				String result = stateless.executeBusiness();
				assertEquals(value, result);
				return null;
			}
		});
	}

}
