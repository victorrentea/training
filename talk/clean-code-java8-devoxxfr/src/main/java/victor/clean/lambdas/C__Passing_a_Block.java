package victor.clean.lambdas;

import java.util.Random;

import lombok.Data;

class EmailService {
	public void sendOrderReceivedEmail(String emailAddress) {
		EmailContext context = new EmailContext(/*smtpConfig, etc*/);
		int MAX_RETRIES = 3;
		for (int i = 0; i < MAX_RETRIES; i++ ) {
			Email email = new Email(); // Generates new ID
			email.setSender("noreply@corp.com");
			email.setReplyTo("/dev/null");
			email.setTo(emailAddress);
			email.setSubject("Order Received");
			email.setBody("Thank you for your order!");
			boolean success = context.send(email);
			if (success) break;
			
		}
	}
}

//-------- supporting (dummy) code ---------
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
