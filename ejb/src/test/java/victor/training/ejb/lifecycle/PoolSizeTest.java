package victor.training.ejb.lifecycle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import victor.training.ejb.EJBTest;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PoolSizeTest extends EJBTest {

	@EJB
	private PoolSize target;

	@Test
	public void test1PoolSizeAfter10Parallel_RAPID_Invocations() throws Throwable {
		executeInParallel(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				target.method(0);
				return null;
			}
		});
		assertTrue(target.getInstanceCount() < 10);
		System.out.println("Number of instances created in the pool: " + target.getInstanceCount());
	}

	@Test
	public void test2PoolSizeAfter10Parallel_SLOW_Invocations() throws Throwable {
		executeInParallel(50, 10, new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				target.method(100);
				return null;
			}
		});
		assertEquals(10, target.getInstanceCount());
		System.out.println("Number of instances created in the pool: " + target.getInstanceCount());
	}
}
