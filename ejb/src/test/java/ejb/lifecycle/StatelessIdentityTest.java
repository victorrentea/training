package ejb.lifecycle;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import ejb.EJBTest;

@RunWith(Arquillian.class)
public class StatelessIdentityTest extends EJBTest {
	@EJB
	private StatelessIdentity target;

	@Test
	public void testSameIdentityUnderTheSameReference() throws Throwable {
		executeInParallel(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				int identity1 = target.getInstanceIdentity();
				int identity2 = target.getInstanceIdentity();
				System.out.println(target.getClass());
				assertEquals(identity1, identity2);
				return null;
			}
		});
	}

}
