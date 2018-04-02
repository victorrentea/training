package victor.clean.lambdas;

import java.util.Random;

import lombok.Data;












//VVVVVVVVV ==== supporting (dummy) code ==== VVVVVVVVV
class EmailContext {
	public boolean send(Email email) {
		return new Random().nextBoolean();
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
