package victor.training.ejb.container;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.ejb.EJBTest;
import victor.training.ejb.container.di.MyDependencyBean;
import victor.training.ejb.container.di.MyDependent;
import victor.training.ejb.container.di.MyDependentBean;

@RunWith(Arquillian.class)
public class DependencyInjectionTest extends EJBTest {

	@EJB
	private MyDependent dependent;

	@Test
	public void testInjection() {
		assertNotNull(dependent.getDependency());
	}
	
	@Test
	public void testInjectedProxyNotImplementation() {
		System.out.println(dependent.getClass());
		System.out.println("Actual Dependent type: " + dependent.getClass());
		assertNotEquals(MyDependencyBean.class, dependent.getDependency().getClass());
	}
	
	@Test
	public void testDependencyInjectionOnlyHappensWhenContainerCreatesInstance() {
		MyDependent manuallyCreated = new MyDependentBean();
		assertNull(manuallyCreated.getDependency());
	}

}
