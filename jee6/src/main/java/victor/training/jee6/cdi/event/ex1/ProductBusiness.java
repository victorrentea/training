package victor.training.jee6.cdi.event.ex1;

import java.util.Date;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class ProductBusiness {
	@Inject
	@Receive
	private Event<ProductEvent> receiveProductEvent;

	@Inject
	@Ship
	private Event<ProductEvent> shipProductEvent;

	public void receiveProduct(Long productId) {
		// do business stuff
		receiveProductEvent.fire(new ProductEvent(productId, new Date()));

		// TOTALK: transactions
	}

	public void shipProduct(Long productId) {
		// do business stuff
		shipProductEvent.fire(new ProductEvent(productId, new Date()));
	}
}
