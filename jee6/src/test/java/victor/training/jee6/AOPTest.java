package victor.training.jee6;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.jee6.cdi.aop.TheInterface;

@RunWith(Arquillian.class)
public class AOPTest extends AbstractArquillianTest {

	@Inject
	private TheInterface theInterface;

	@Test
	public void invokeDecoratedMethod1() {
		theInterface.method1();
	}
}
