package victor.training.ejb.container;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.ejb.EJBTest;
import victor.training.ejb.container.aop.MyCleanBusinessService;

@RunWith(Arquillian.class)
public class AOPTest extends EJBTest {
	
	@EJB
	private MyCleanBusinessService target;
	
	@Test
	public void testCallAdvicedBusinessMethod() {
		target.myBusinessCall("buzz parameter");
	}
	
}
