package victor.training.eepatterns;

import static org.junit.Assert.assertNotNull;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.eepatterns.legacypojo.POJOAsCDI;
import victor.training.eepatterns.legacypojo.POJOAsCDIWithFactory;
import victor.training.eepatterns.legacypojo.POJOAsEJB;


@RunWith(Arquillian.class)
public class LegacyPOJOTest extends AbstractArquillianTest {

	@EJB
	private POJOAsEJB asEJB;

	@Inject
	private POJOAsCDI asCDI;

	@Inject
	private POJOAsCDIWithFactory asCDIWithFactory;

	@Test
	public void integratedPOJOAsEJB() {
		assertNotNull(asEJB);
		asEJB.doStuff();
	}

	@Test
	public void integratedPOJOAsCDI() {
		assertNotNull(asCDI);
		asCDI.doStuff();
	}

	@Test
	public void integratedPOJOAsCDIWithFactory() {
		assertNotNull(asCDIWithFactory);
		asCDIWithFactory.doStuff();
	}

}
