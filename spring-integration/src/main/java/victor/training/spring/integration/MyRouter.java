package victor.training.spring.integration;

import org.springframework.messaging.Message;

public class MyRouter {
public String route(Message<String> message) {
	System.out.println("Message: "+ message);
	return "log-queue";
}
}
