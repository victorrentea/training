package victor.training.jee6.cdi.event.ex1;

import javax.enterprise.event.Observes;

public class ProductEventListener {

	public void onProductReceived(@Observes @Receive ProductEvent event) {
		System.out.println("Product " + event.getProductId() + " Received");
	}

	public void onProductShipped(@Observes @Ship ProductEvent event) {
		System.out.println("Product " + event.getProductId() + " Shipped");
	}
}
