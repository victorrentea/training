package victor.training.jee6;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.jee6.cdi.di.Dependent;

@RunWith(Arquillian.class)
public class DITest extends AbstractArquillianTest {

	@Inject
	private Dependent target;

	@Test
	public void playDI() {
		target.doStuffWithDependencies();
		System.out.println(target);
	}
}
