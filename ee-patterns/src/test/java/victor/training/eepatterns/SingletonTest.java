package victor.training.eepatterns;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.eepatterns.singleton.SMSBusiness;


@RunWith(Arquillian.class)
public class SingletonTest extends AbstractArquillianTest {

	@Inject
	private SMSBusiness smsBusiness;
	
	@Test
	public void testGatekeeper() throws Throwable {
		executeInParallel(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				smsBusiness.sendSMS();
				return null;
			}
		});
	}
}
