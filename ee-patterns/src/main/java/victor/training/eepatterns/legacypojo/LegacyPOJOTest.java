package victor.training.eepatterns.legacypojo;

import static org.junit.Assert.assertNotNull;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@Startup
public class LegacyPOJOTest{

	@EJB
	private POJOAsEJB asEJB;

	@Inject
	private POJOAsCDI asCDI;

	@Inject
	private POJOAsCDIWithFactory asCDIWithFactory;

	@PostConstruct
	public void testAllDependencies() {
		integratedPOJOAsCDI();
		integratedPOJOAsCDIWithFactory();
		integratedPOJOAsEJB();
		System.out.println("Tests OK");
	}
	
	public void integratedPOJOAsEJB() {
		assertNotNull(asEJB);
		asEJB.doStuff();
	}

	public void integratedPOJOAsCDI() {
		assertNotNull(asCDI);
		asCDI.doStuff();
	}

	public void integratedPOJOAsCDIWithFactory() {
		assertNotNull(asCDIWithFactory);
		asCDIWithFactory.doStuff();
	}

}
