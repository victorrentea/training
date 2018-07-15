package victor.training.oo.behavioral.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class ObserverInSpring {
	public static void main(String[] args) {
		SpringApplication.run(ObserverInSpring.class, args);
	}
}

class OrderPlaced {
	public final long orderId;
	public OrderPlaced(long orderId) {
		this.orderId = orderId;
	}
}
class OrderInStock {
	public final long orderId;
	public OrderInStock(long orderId) {
		this.orderId = orderId;
	}
}

@Component
class Start implements CommandLineRunner{
	@Autowired
	private ApplicationEventPublisher publisher;
	public void run(String... args) throws Exception {
		publisher.publishEvent(new OrderPlaced(13)); // SOLUTION
	}
}

@Service
class StockManagementService {
	@EventListener // SOLUTION
	//public void handle(OrderPlaced event) {// INITIAL
	public OrderInStock handle(OrderPlaced event) {
		System.out.println("Checking stock for products in order id: " + event.orderId);
		return new OrderInStock(event.orderId);// SOLUTION
	}
}
@Service
class InvoiceService {
	@EventListener // SOLUTION (
	public void handle(OrderInStock event) {
		System.out.println("Invoice customer for order id: " + event.orderId);
	} // SOLUTION )
}