package victor.training.eepatterns;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.eepatterns.contextholder.thread.MyServlet;


@RunWith(Arquillian.class)
public class ContextHolderTest extends AbstractArquillianTest {

	@Inject
	private MyServlet threadServlet;

	@Test
	public void testThreadLocalContext() {
		threadServlet.processHTTPRequest();
	}

}
