package victor.training.jee6;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import victor.training.jee6.cdi.event.ex1.ProductBusiness;
import victor.training.jee6.cdi.event.ex2.BatchRunner;

@RunWith(Arquillian.class)
public class EventsTest extends AbstractArquillianTest {

	@Inject
	private ProductBusiness productBusiness;

	@Inject
	private BatchRunner batchRunner;

	@Test
	public void runEventsExample1() throws InterruptedException {
		Thread.sleep(2 * 1000);
		System.out.println("========== Running Events example 1 ===========");
		productBusiness.receiveProduct(10L);
		productBusiness.receiveProduct(11L);
		productBusiness.shipProduct(10L);
	}

	@Test
	public void runEventsExample2() throws InterruptedException {
		Thread.sleep(2 * 1000);
		System.out.println("========== Running Events example 2 ===========");
		batchRunner.runBatchJobs();
		batchRunner.runBatchJobs();
		batchRunner.runBatchJobs();

	}
}
