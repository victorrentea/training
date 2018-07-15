package victor.training.oo.behavioral.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ObserverInSpring {
	public static void main(String[] args) {
		SpringApplication.run(ObserverInSpring.class, args);
	}
}

@Component
class Start implements CommandLineRunner{

	@Autowired
	private ApplicationEventPublisher publisher;
	
	public void run(String... args) throws Exception {
		publisher.publishEvent("Aloha, un string!"); // SOLUTION
	}
}

@Component
class Interested {
	@EventListener // SOLUTION
	public void handle(String msg) {
		System.out.println("Out: " + msg);
	}
}
@Component
class Interested2 {
	@EventListener // SOLUTION
	public void handle(String msg) {
		System.out.println("Out: " + msg);
	}
}