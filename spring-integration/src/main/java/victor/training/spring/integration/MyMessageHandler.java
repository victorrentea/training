package victor.training.spring.integration;

public class MyMessageHandler {

	public String handle(String message) {
		return "Pong:" + message;
	}
}
