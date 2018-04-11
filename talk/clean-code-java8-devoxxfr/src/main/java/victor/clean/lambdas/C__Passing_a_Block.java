package victor.clean.lambdas;

import java.util.Random;
import java.util.function.Consumer;

import lombok.Data;


// VVVVVVVVV ==== supporting (dummy) code ==== VVVVVVVVV
class EmailContext implements AutoCloseable {
	public boolean send(Email email) {
		boolean r = new Random().nextBoolean();
		System.out.println("Sending "+(r?"OK":"KO")+" email " + email.getSubject());
		return r;
	}

	public void close() {
	}
}

@Data
class Email {
	private String sender;
	private String subject;
	private String body;
	private String replyTo;
	private String to;
}
